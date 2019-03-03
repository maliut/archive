#include <string>
#include <vector>
#include <map>
#include <regex>
#include <iostream>
#include <fstream>
using namespace std;

// declaration
void read_source(char* filename);
void include_files();
void parse();
string replace_symbol(string line, string(*symbol_formatter)(string symbol));

vector<string> output_lines;       // 最终要输出的内容
map<string, string> symbols;  // #define 定义的符号
ofstream output;	// 准备文件

// implement
int main(int argc, char* argv[]) {
	output.open("output.txt");
	read_source(argv[1]);
	include_files();
	parse();
	output.close();
}

void read_source(char* filename) {
	ifstream source;
	source.open(filename, ios::in);
	while (!source.eof()) {
		string line;
		getline(source, line, '\n');
		output_lines.push_back(line);
	}
	source.close();
}

void read_include_file(string filename);
vector<string> include_lines_tmp;  // 需要 include 的文件的临时存放处
void include_files() {
	const regex pattern("^\\s*#include\\s*\"(.+)\"\\s*$");
	match_results<string::const_iterator> result;
	// search every line
	vector<string> filenames;
	vector<string>::iterator it;
	for (it = output_lines.begin(); it != output_lines.end(); it++) {
		bool matches = regex_search(*it, result, pattern);
		if (matches && result[1] != "iostream") {
			read_include_file(result[1]);
			vector<string>::iterator itmp;
			int position = it - output_lines.begin();
			for (itmp = include_lines_tmp.begin(); itmp != include_lines_tmp.end(); itmp++) {
				output_lines.insert(output_lines.begin() + position, *itmp);
				position++;
			}
			output_lines.erase(output_lines.begin() + position);
			it = output_lines.begin();
		}
	}
}

void read_include_file(string filename) {
	include_lines_tmp.clear();
	ifstream source;
	source.open(filename, ios::in);
	while (!source.eof()) {
		string line;
		getline(source, line, '\n');
		include_lines_tmp.push_back(line);
	}
}

bool search_symbol(string key);
bool delete_symbol(string key);
void add_symbol_with_parameter(string sign, string parameter, string content);
string replace_symbol_if_exist(string symbol);
void parse() {
	const regex pattern_define_with_parameter("^\\s*#define\\s+([A-Za-z_][A-Za-z0-9_]*)\\(([A-Za-z_][A-Za-z0-9_]*)\\)\\s*(.*)$");
	const regex pattern_define("^\\s*#define\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*(.*)$");
	const regex pattern_undef("^\\s*#undef\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*$");
	const regex pattern_ifdef("^\\s*#ifdef\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*$");
	const regex pattern_ifndef("^\\s*#ifndef\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*$");
	const regex pattern_if("^\\s*#if\\s+(\\d+)\\s*$");
	const regex pattern_else("^\\s*#else\\s*$");
	const regex pattern_endif("^\\s*#endif\\s*$");
	match_results<string::const_iterator> result;

	bool valid = true;    // 是否需要保留语句？
	bool in_if_clause = false;   // 是否在 if 语句中(不用处理多重嵌套)
	vector<string>::iterator it;
	for (it = output_lines.begin(); it != output_lines.end(); it++) {
		if (regex_search(*it, result, pattern_define_with_parameter)) {
			add_symbol_with_parameter(result[1], result[2], result[3]);
		} else if (regex_search(*it, result, pattern_define)) {
			symbols[result[1]] = result[2];
		} else if (regex_search(*it, result, pattern_undef)) {
			delete_symbol(result[1]);
		} else if (regex_search(*it, result, pattern_ifdef)) {
			in_if_clause = true;
			valid = search_symbol(result[1]);
		} else if (regex_search(*it, result, pattern_ifndef)) {
			in_if_clause = true;
			valid = !search_symbol(result[1]);
		} else if (regex_search(*it, result, pattern_if)) {
			in_if_clause = true;
			string s = result[1];
			valid = !(atoi(s.c_str()) == 0);
		} else if (regex_search(*it, result, pattern_else)) {
			valid = !valid;
		} else if (regex_search(*it, result, pattern_endif)) {
			in_if_clause = false;
			valid = true;
		} else {
			if (valid) output << replace_symbol(*it, replace_symbol_if_exist) << endl;
		}
	}
}

bool search_symbol(string key) {
	map<string, string >::iterator it;
	it = symbols.find(key);
	return (it == symbols.end()) ? false : true;
}

bool delete_symbol(string key) {
	map<string, string >::iterator it;
	it = symbols.find(key);
	if (it == symbols.end()) {
		return false;
	} else {
		symbols.erase(it);
		return true;
	}
}

bool is_symbol_letter(char c, bool is_first_letter = false);
string& replace_all(string& str, const string& old_value, const string& new_value);
string replace_symbol(string line, string (*symbol_formatter)(string symbol)) {
	line.append(" ");  // 防止最后一个是 symbol 的情况
	string output_line = "";	// 输出的行（去掉了注释）
	bool in_string = false, in_char = false, in_symbol = false, in_comment = false; // 当前的状态
	string current_symbol = "";  // 当前的符号
	int length = line.size();
	for (int i = 0; i < length; i++) {
		char current = line.at(i);
		if (in_string) {  // 在 string 里面就不做匹配了，所以不考虑 string 里的注释，下同
			if (current == '"') in_string = false;
			output_line.append(1, current);
		} else if (in_char) {
			if (current == '\'') in_char = false;
			output_line.append(1, current);
		} else if (in_comment) {
			if (current == '*' && i != length - 1 && line.at(i + 1) == '/') in_comment = false;
		} else if (in_symbol) {
			if (is_symbol_letter(current)) {	// 还在符号里面
				current_symbol.append(1, current);
			} else {	// 一个符号结束了，对当前符号查找是否要替换
				in_symbol = false;
				string after_format = symbol_formatter(current_symbol);
				if (symbol_formatter == replace_symbol_if_exist && after_format == current_symbol) {
					// 在正文处理阶段，而且没有检测到替换，那么还需要检测是不是 #define 的函数
					if (current == '(') {	// 是左括号
						current_symbol.append("(");
						string after_format_2 = symbol_formatter(current_symbol);
						if (after_format_2 != current_symbol) {	// 果然是一个预定义的函数,要提取出参数
							int depth = 0;
							string parameter = "";
							for (i++; i < length; i++) {
								if (line.at(i) == '(') {
									depth++;
									parameter.append(1, line.at(i));
								} else if (line.at(i) == ')') {
									if (depth > 0) {
										depth--;
										parameter.append(1, line.at(i));
									} else {	// 提取参数结束,开始替换
										output_line.append(replace_all(after_format_2, "__parameter__", parameter));
										break;
									}
								} else {
									parameter.append(1, line.at(i));
								}
							}
							continue;
						} 
					}
				} 
				output_line.append(after_format);
				output_line.append(1, current);
			}
		} else {
			if (current == '"') {
				in_string = true;
				output_line.append(1, current);
			} else if (current == '\'') {
				in_char = true;
				output_line.append(1, current);
			} else if (current == '/' && i != length - 1 && line.at(i + 1) == '/') {
				break; //return;		// 单行注释，下面的内容都不考虑了
			} else if (current == '/' && i != length - 1 && line.at(i + 1) == '*') {
				in_comment = true;		// 行内注释，注释不输出
			} else if (is_symbol_letter(current, true)) {   // 是一个 symbol 的首字符
				in_symbol = true;
				current_symbol = "";   // clear
				current_symbol.append(1, current);   // 开始收集
			} else {	// 其他乱七八糟的东西，比如运算符等
				output_line.append(1, current);
			}
		}
	}
	return output_line;
}

bool is_symbol_letter(char c, bool is_first_letter) {
	if ('a' <= c && c <= 'z') return true;
	if ('A' <= c && c <= 'Z') return true;
	if (c == '_') return true;
	if ((!is_first_letter) && ('0' <= c && c <= '9')) return true;
	return false;
}

// 判断解析到的符号是不是需要替换成之前定义过的符号
string replace_symbol_if_exist(string symbol) {
	return search_symbol(symbol) ? symbols[symbol] : symbol;
}

// part3 相关
// 判断宏定义函数的实现部分中的一个符号是不是参数
string parameter_name = "";
string replace_symbol_if_is_parameter(string symbol) {
	return (parameter_name == symbol) ? "__parameter__" : symbol;
}

void add_symbol_with_parameter(string sign, string parameter, string content) {
	parameter_name = parameter;
	string normalize_content = replace_symbol(content, replace_symbol_if_is_parameter);
	sign.append("(");	// 符号加上左括号以示区分
	symbols[sign] = normalize_content;
}

string& replace_all(string& str, const string& old_value, const string& new_value) {
	for (string::size_type pos(0); pos != string::npos; pos += new_value.length()) {
		if ((pos = str.find(old_value, pos)) != string::npos)
			str.replace(pos, old_value.length(), new_value);
		else break;
	}
	return str;
}

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

vector<string> output_lines;       // ����Ҫ���������
map<string, string> symbols;  // #define ����ķ���
ofstream output;	// ׼���ļ�

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
vector<string> include_lines_tmp;  // ��Ҫ include ���ļ�����ʱ��Ŵ�
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

	bool valid = true;    // �Ƿ���Ҫ������䣿
	bool in_if_clause = false;   // �Ƿ��� if �����(���ô������Ƕ��)
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
	line.append(" ");  // ��ֹ���һ���� symbol �����
	string output_line = "";	// ������У�ȥ����ע�ͣ�
	bool in_string = false, in_char = false, in_symbol = false, in_comment = false; // ��ǰ��״̬
	string current_symbol = "";  // ��ǰ�ķ���
	int length = line.size();
	for (int i = 0; i < length; i++) {
		char current = line.at(i);
		if (in_string) {  // �� string ����Ͳ���ƥ���ˣ����Բ����� string ���ע�ͣ���ͬ
			if (current == '"') in_string = false;
			output_line.append(1, current);
		} else if (in_char) {
			if (current == '\'') in_char = false;
			output_line.append(1, current);
		} else if (in_comment) {
			if (current == '*' && i != length - 1 && line.at(i + 1) == '/') in_comment = false;
		} else if (in_symbol) {
			if (is_symbol_letter(current)) {	// ���ڷ�������
				current_symbol.append(1, current);
			} else {	// һ�����Ž����ˣ��Ե�ǰ���Ų����Ƿ�Ҫ�滻
				in_symbol = false;
				string after_format = symbol_formatter(current_symbol);
				if (symbol_formatter == replace_symbol_if_exist && after_format == current_symbol) {
					// �����Ĵ���׶Σ�����û�м�⵽�滻����ô����Ҫ����ǲ��� #define �ĺ���
					if (current == '(') {	// ��������
						current_symbol.append("(");
						string after_format_2 = symbol_formatter(current_symbol);
						if (after_format_2 != current_symbol) {	// ��Ȼ��һ��Ԥ����ĺ���,Ҫ��ȡ������
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
									} else {	// ��ȡ��������,��ʼ�滻
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
				break; //return;		// ����ע�ͣ���������ݶ���������
			} else if (current == '/' && i != length - 1 && line.at(i + 1) == '*') {
				in_comment = true;		// ����ע�ͣ�ע�Ͳ����
			} else if (is_symbol_letter(current, true)) {   // ��һ�� symbol �����ַ�
				in_symbol = true;
				current_symbol = "";   // clear
				current_symbol.append(1, current);   // ��ʼ�ռ�
			} else {	// �������߰���Ķ����������������
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

// �жϽ������ķ����ǲ�����Ҫ�滻��֮ǰ������ķ���
string replace_symbol_if_exist(string symbol) {
	return search_symbol(symbol) ? symbols[symbol] : symbol;
}

// part3 ���
// �жϺ궨�庯����ʵ�ֲ����е�һ�������ǲ��ǲ���
string parameter_name = "";
string replace_symbol_if_is_parameter(string symbol) {
	return (parameter_name == symbol) ? "__parameter__" : symbol;
}

void add_symbol_with_parameter(string sign, string parameter, string content) {
	parameter_name = parameter;
	string normalize_content = replace_symbol(content, replace_symbol_if_is_parameter);
	sign.append("(");	// ���ż�����������ʾ����
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

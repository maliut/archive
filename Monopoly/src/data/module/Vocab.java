package data.module;

public class Vocab {
	
	/** Greeting */
	public static final String Greeting = "欢迎来到大富翁的世界！\n按s读取存档，按x结束游戏，其余按键开始游戏。\n";
	
	/** Name input prompt */
	public static final String NameInputPrompt = "请设定角色%d的名字：";
	
	/** Map Re-generate prompt */
	public static final String MapRegeneratePrompt = "输入r重新生成地图，其余任意键接受地图";
	
	/** Date output */
	public static final String DateShowFormat = "今天是yyyy年MM月dd日\n";
	
	/** Player state */
	public static final String CurrentPlayerInfo = "现在是%s的操作时间，您的前进方向是%s。\n";
	public static final String[] PlayerDirection = {"顺时针","","逆时针"};
	public static final String PlayerSlowState = "当前的减速状态还剩%d回合\n";
	public static final String PlayerFineFreeState = "当前的免除过路费状态还剩%d回合\n";
	
	/** Menu */
	public static final String[] Command = {
		"查看地图",
		"查看原始地图",
		"使用道具",
		"前方十步内预警",
		"查看前后指定步数的具体信息",
		"查看玩家的资产信息",
		"心满意足扔骰子",
		"存档",
		"认输"
	};
	
	/** Player icon */
	public static final String[] PlayerIcon = {
		"",
		"○\u3000",   // player 1
		"□\u3000"    // player 2
	};
	
	/** Cell icon */
	public static final String AvailableCell = "<可供出售>";
	public static final String[] CellIcon = {
		"◎\u3000",   // cell for sold
		"道\u3000",   // item store
		"银\u3000",   // bank
		"新\u3000",   // news
		"彩\u3000",   // lottery
		"卡\u3000",   // get an item
		"券\u3000",   // get coupon
		"●\u3000",   // cell of player 1
		"■\u3000"    // cell of player 2
	};
	
	
	/** Info of certain cell */
	public static final String CellGreeting = "欢迎来到%s";
	public static final String StepInputPrompt = "请输入你想查询的点与你相差的步数（后方用负数，输入任意非数字退出）：";
	public static final String[] CellTypeName = {
		"地产\n",
		"道具商店\n",
		"银行\n",
		"新闻\n",
		"彩票\n",
		"赠送道具点\n",
		"赠送点券点\n"
	};
	public static final String[] StreetName = {
		"",     // when 0, the cell is not for sell
		"天元镇#%d\n",
		"道吉镇#%d\n",
		"104号道路#%d\n",
		"金水市#%d\n",
		"山木镇#%d\n",
		"卡依市#%d\n",
		"银叶市#%d\n",
		"西达镇#%d\n"
	};
	public static final String[] CellInfoListHead = {
		"类型：",
		"名称：",
		"初始价格：",
		"当前等级：",
		"拥有者："
	};
	
	/** Players info */
	public static final String ShowPlayersInfoPrompt = "玩家资产信息如下：\n";
	public static final String[] PlayersInfoListHead = {
		"玩家名","点券","现金","存款","房产","资产总额"
	};
	
	/** Game info */
	public static final String BarrierInfo = "前方第%d步处为路障，请注意。\n";
	public static final String NoBarrierInfo = "前方%d步内没有路障。\n";
	
	public static final String DiceInfo = "你掷得的点数为%d\n";
	
	public static final String ShowEndGame = "游戏结束！玩家%s取得了胜利！\n";
	
	public static final String GetInfo = "玩家%s获得了%s.\n";
	public static final String LossInfo = "玩家%s损失了%d%s.\n";
	public static final String CellLossInfo = "玩家%s损失了%s.\n";
	
	/** Lottery related */
	public static final String LotteryInfo = "你来到了彩票点。\n恭喜！你中了%s等奖！\n";
	public static final String LotteryMissInfo = "你来到了彩票点。\n很遗憾，你没有中奖。\n";
	public static final String[] LotteryLV = {"","一","二","三"};
	
	/** Field related */
	public static final String buyPrompt = "是否购买？（1-确定，0-取消）";
	public static final String levelUpPrompt = "是否升级？（1-确定，0-取消）";
	
	/** Bank related */
	public static final String bankPrompt = "您当前的现金是%d，存款是%d.\n请选择操作？（0-存钱，1-取钱，2-退出.）";
	public static final String bankSavePrompt = "请输入想要存入的金额：";
	public static final String bankGetPrompt = "请输入想要取出的金额：";
	
	/** News related */
	public static final String[] News = {
		"新闻：奖励土地价值最高者%s%d现金！\n",
		"新闻：补助土地价值最低者%s%d现金！\n",
		"新闻：银行加发储金红利，每个人得到存款10%！\n",
		"新闻：所有人缴纳财产税，每个人扣除存款10%！\n",
		"新闻：每个人得到一张卡片！\n"
	};
	
	/** Item shop related */
	public static final String showItemBuyPrompt = "请选择你要买的道具，输入0退出！\n";
	public static final String[] ItemName = {
		"","转向卡","乌龟卡","路障","购地卡","查税卡","均富卡","拆迁卡","怪兽卡","财神卡","福神卡"
	}; 
	
	/** Barrier related */
	public static final String BarrierSetPrompt = "请选择相对你当前位置的放置位置（-8 ~ 8）：";
	public static final String BarrierBlockInfo = "你遇到了路障！\n";
	
	/** Error vocab */
	public static final String NoError = "操作成功！\n";
	public static final String InputError = "不可用的输入值！请重新输入：";
	public static final String LackOfCashError = "您当前的现金不足！\n";
	public static final String LackOfCouponError = "您当前的点券不足！\n";
	public static final String LackOfDepositError = "您当前的存款不足！\n";
	public static final String LackOfItemError = "您当前无此道具！\n";
	public static final String IllegalItemUseError = "您无法在当前位置使用此道具！\n";
	public static final String NoSavesError = "找不到存档！\n";
	public static final String UnknownSaveError = "存档时遇到未知错误！游戏将继续进行。\n";
	public static final String UnknownLoadError = "读档时遇到未知错误！游戏将退出。";
}

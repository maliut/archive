package data.module;

public class Vocab {
	
	/** Greeting */
	public static final String Greeting = "��ӭ�������̵����磡\n��s��ȡ�浵����x������Ϸ�����ఴ����ʼ��Ϸ��\n";
	
	/** Name input prompt */
	public static final String NameInputPrompt = "���趨��ɫ%d�����֣�";
	
	/** Map Re-generate prompt */
	public static final String MapRegeneratePrompt = "����r�������ɵ�ͼ��������������ܵ�ͼ";
	
	/** Date output */
	public static final String DateShowFormat = "������yyyy��MM��dd��\n";
	
	/** Player state */
	public static final String CurrentPlayerInfo = "������%s�Ĳ���ʱ�䣬����ǰ��������%s��\n";
	public static final String[] PlayerDirection = {"˳ʱ��","","��ʱ��"};
	public static final String PlayerSlowState = "��ǰ�ļ���״̬��ʣ%d�غ�\n";
	public static final String PlayerFineFreeState = "��ǰ�������·��״̬��ʣ%d�غ�\n";
	
	/** Menu */
	public static final String[] Command = {
		"�鿴��ͼ",
		"�鿴ԭʼ��ͼ",
		"ʹ�õ���",
		"ǰ��ʮ����Ԥ��",
		"�鿴ǰ��ָ�������ľ�����Ϣ",
		"�鿴��ҵ��ʲ���Ϣ",
		"��������������",
		"�浵",
		"����"
	};
	
	/** Player icon */
	public static final String[] PlayerIcon = {
		"",
		"��\u3000",   // player 1
		"��\u3000"    // player 2
	};
	
	/** Cell icon */
	public static final String AvailableCell = "<�ɹ�����>";
	public static final String[] CellIcon = {
		"��\u3000",   // cell for sold
		"��\u3000",   // item store
		"��\u3000",   // bank
		"��\u3000",   // news
		"��\u3000",   // lottery
		"��\u3000",   // get an item
		"ȯ\u3000",   // get coupon
		"��\u3000",   // cell of player 1
		"��\u3000"    // cell of player 2
	};
	
	
	/** Info of certain cell */
	public static final String CellGreeting = "��ӭ����%s";
	public static final String StepInputPrompt = "�����������ѯ�ĵ��������Ĳ��������ø�������������������˳�����";
	public static final String[] CellTypeName = {
		"�ز�\n",
		"�����̵�\n",
		"����\n",
		"����\n",
		"��Ʊ\n",
		"���͵��ߵ�\n",
		"���͵�ȯ��\n"
	};
	public static final String[] StreetName = {
		"",     // when 0, the cell is not for sell
		"��Ԫ��#%d\n",
		"������#%d\n",
		"104�ŵ�·#%d\n",
		"��ˮ��#%d\n",
		"ɽľ��#%d\n",
		"������#%d\n",
		"��Ҷ��#%d\n",
		"������#%d\n"
	};
	public static final String[] CellInfoListHead = {
		"���ͣ�",
		"���ƣ�",
		"��ʼ�۸�",
		"��ǰ�ȼ���",
		"ӵ���ߣ�"
	};
	
	/** Players info */
	public static final String ShowPlayersInfoPrompt = "����ʲ���Ϣ���£�\n";
	public static final String[] PlayersInfoListHead = {
		"�����","��ȯ","�ֽ�","���","����","�ʲ��ܶ�"
	};
	
	/** Game info */
	public static final String BarrierInfo = "ǰ����%d����Ϊ·�ϣ���ע�⡣\n";
	public static final String NoBarrierInfo = "ǰ��%d����û��·�ϡ�\n";
	
	public static final String DiceInfo = "�����õĵ���Ϊ%d\n";
	
	public static final String ShowEndGame = "��Ϸ���������%sȡ����ʤ����\n";
	
	public static final String GetInfo = "���%s�����%s.\n";
	public static final String LossInfo = "���%s��ʧ��%d%s.\n";
	public static final String CellLossInfo = "���%s��ʧ��%s.\n";
	
	/** Lottery related */
	public static final String LotteryInfo = "�������˲�Ʊ�㡣\n��ϲ��������%s�Ƚ���\n";
	public static final String LotteryMissInfo = "�������˲�Ʊ�㡣\n���ź�����û���н���\n";
	public static final String[] LotteryLV = {"","һ","��","��"};
	
	/** Field related */
	public static final String buyPrompt = "�Ƿ��򣿣�1-ȷ����0-ȡ����";
	public static final String levelUpPrompt = "�Ƿ���������1-ȷ����0-ȡ����";
	
	/** Bank related */
	public static final String bankPrompt = "����ǰ���ֽ���%d�������%d.\n��ѡ���������0-��Ǯ��1-ȡǮ��2-�˳�.��";
	public static final String bankSavePrompt = "��������Ҫ����Ľ�";
	public static final String bankGetPrompt = "��������Ҫȡ���Ľ�";
	
	/** News related */
	public static final String[] News = {
		"���ţ��������ؼ�ֵ�����%s%d�ֽ�\n",
		"���ţ��������ؼ�ֵ�����%s%d�ֽ�\n",
		"���ţ����мӷ����������ÿ���˵õ����10%��\n",
		"���ţ������˽��ɲƲ�˰��ÿ���˿۳����10%��\n",
		"���ţ�ÿ���˵õ�һ�ſ�Ƭ��\n"
	};
	
	/** Item shop related */
	public static final String showItemBuyPrompt = "��ѡ����Ҫ��ĵ��ߣ�����0�˳���\n";
	public static final String[] ItemName = {
		"","ת��","�ڹ꿨","·��","���ؿ�","��˰��","������","��Ǩ��","���޿�","����","����"
	}; 
	
	/** Barrier related */
	public static final String BarrierSetPrompt = "��ѡ������㵱ǰλ�õķ���λ�ã�-8 ~ 8����";
	public static final String BarrierBlockInfo = "��������·�ϣ�\n";
	
	/** Error vocab */
	public static final String NoError = "�����ɹ���\n";
	public static final String InputError = "�����õ�����ֵ�����������룺";
	public static final String LackOfCashError = "����ǰ���ֽ��㣡\n";
	public static final String LackOfCouponError = "����ǰ�ĵ�ȯ���㣡\n";
	public static final String LackOfDepositError = "����ǰ�Ĵ��㣡\n";
	public static final String LackOfItemError = "����ǰ�޴˵��ߣ�\n";
	public static final String IllegalItemUseError = "���޷��ڵ�ǰλ��ʹ�ô˵��ߣ�\n";
	public static final String NoSavesError = "�Ҳ����浵��\n";
	public static final String UnknownSaveError = "�浵ʱ����δ֪������Ϸ���������С�\n";
	public static final String UnknownLoadError = "����ʱ����δ֪������Ϸ���˳���";
}

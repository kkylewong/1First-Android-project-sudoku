package sudoku.model.gameData;

public class GameBase {
	/*
	 * class to store Sudoku data
	 */
	private final String test = "037984621" + "462531978" + "819276345"
			+ "756198432" + "284653719" + "319427856" + "645719283"
			+ "173862594" + "928345167";

	private final String level1_1 = "900020401005090020040100097"
			+ "500001000000000002400570309" + "053002180000000000002800003";

	private final String level1_1_answer = "987625421" + "315497628"
			+ "246138597" + "539281746" + "671349852" + "428576319"
			+ "753962184" + "894713265" + "162854973";

	private final String level1_2 = "007000001900000628602000590"
			+ "000081005070503000090060070" + "000005000200040086040030000";

	private final String level1_2_answer = "587926431" + "914357628"
			+ "632814597" + "326781945" + "478593162" + "195462873"
			+ "869275314" + "253149786" + "741638259";

	private final String level1_3 = "000090401390701000800000090"
			+ "000210045500000080930000010" + "000029800000106003400000000";
	private final String level1_3_answer = "257698431394751628816432597"
			+ "678213945541967382932584716" + "165329874789146253423875169";

	private final String level1_4 = "007000001190000020040306000"
			+ "000830700000041000086702000" + "000000074300100005000000980";
	private final String level1_4_answer = "657928431193475628842316597"
			+ "521839746739641852486752319" + "965283174378194265214567983";

	private final String level1_5 = "080900030009100000600000507"
			+ "002540006030002000170000003" + "003000704200308060000000000";
	private final String level1_5_answer = "587926431349157628621834597"
			+ "892543176436712859175689243" + "953261784214378965768495312";

	private final String level2_1 = "007000000300041020810200090"
			+ "000000700600009010000387000" + "009036070000000803006004000";
	private final String level2_1_answer = "267598431395741628814263597"
			+ "943612785678459312152387946" + "589136274421975863736824159";

	private final String level2_2 = "000000030934017600200008097"
			+ "400090000300000080050061300" + "000209000000000005620004000";
	private final String level2_2_answer = "587926431934517628261438597"
			+ "418392756396745182752861349" + "173259864849673215625184973";

	private final String level2_3 = "267090001000000020010004000"
			+ "005970006003050000000300109" + "070005000004000075500400963";
	private final String level2_3_answer = "267598431459713628318624597"
			+ "145972386693851742782346159" + "976235814834169275521487963";

	private final String level2_4 = "650008000000730000200400000"
			+ "000000045001090060400000000" + "070009014540100076000000209";
	private final String level2_4_answer = "657928431914735628283461597"
			+ "726813945831594762495276183" + "372659814549182376168347259";

	private final String level2_5 = "050000400091000600200001007"
			+ "000200000380000000700089013" + "000600070000030065900000080";
	private final String level2_5_answer = "657928431491357628238461597"
			+ "519273846382146759746589213" + "823695174174832965965714382";

	private final String level3_1 = "000000430000010620081000000"
			+ "700900000030750960010000003" + "075009200000000000600835070";
	private final String level3_1_answer = "267598431953417628481326597"
			+ "726943815834751962519682743" + "375169284198274356642835179";

	private final String level3_2 = "087000030000700008000000507"
			+ "003900000001030740000008250" + "309060000000402000500080062";
	private final String level3_2_answer = "987625431135749628642813597"
			+ "273954186851236749496178253" + "329561874768492315514387962";

	private final String level3_3 = "";
	private final String level3_4 = "";
	private final String level3_5 = "";

	private RandomGenerator addShudu;
	private Slove slove;
	private String b = "";
	private int[][] a;

	public GameBase() {
		addShudu = new RandomGenerator();
	}

	// enter level one or two
	public String getShudu(int oneOrTwo) {
		a = addShudu.add(oneOrTwo);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				b = b + a[i][j];
			}
		}
		System.out.println("GameBase -new shudu-->> " + b);
		return b;
	}

	public String getShuduAnswer() {
		slove = new Slove(a);
		String c = slove.resolve();
		System.out.println("GameBase -new shudu answer-->> " + c);
		return c;
	}

	public String returnStr(int level) {
		String a = "";
		switch (level) {
		case 11:
			a = level1_1;
			break;
		case 12:
			a = level1_2;
			break;
		case 13:
			a = level1_3;
			break;
		case 14:
			a = level1_4;
			break;
		case 15:
			a = level1_5;
			break;
		case 21:
			a = level2_1;
			break;
		case 22:
			a = level2_2;
			break;
		case 23:
			a = level2_3;
			break;
		case 24:
			a = level2_4;
			break;
		case 25:
			a = level2_5;
			break;
		case 31:
			a = level3_1;
			break;
		case 32:
			a = test;
			break;
		case 111:
			a = level1_1_answer;
			break;
		case 112:
			a = level1_2_answer;
			break;
		case 113:
			a = level1_3_answer;
			break;
		case 114:
			a = level1_4_answer;
			break;
		case 115:
			a = level1_5_answer;
			break;
		case 121:
			a = level2_1_answer;
			break;
		case 122:
			a = level2_2_answer;
			break;
		case 123:
			a = level2_3_answer;
			break;
		case 124:
			a = level2_4_answer;
			break;
		case 125:
			a = level2_5_answer;
			break;
		case 131:
			a = level3_1_answer;
			break;
		case 132:
			a = level3_2_answer;
			break;
		// random sudoku
		case 1:
			a = getShudu(1);
			break;
		case 2:
			a = getShudu(2);
			break;
		}
		return a;
	}

}

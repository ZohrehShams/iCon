// $ANTLR 3.5.2 SpiderDiagrams.g 2019-02-21 18:43:41

package speedith.core.lang.reader;
import static speedith.core.i18n.Translations.i18n;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class SpiderDiagramsParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "CD", "CD_BINARY", "CD_Car", "CD_Car_BINARY", 
		"COMMENT", "COP", "CompleteCOP", "DICT", "ESC_SEQ", "HEX_DIGIT", "ID", 
		"IdentifierPart", "IdentifierStart", "LIST", "LUCOP", "LUCarCOP", "OCTAL_ESC", 
		"PAIR", "PD", "SD_BINARY", "SD_COMPOUND", "SD_FALSE", "SD_NULL", "SD_PRIMARY", 
		"SD_UNARY", "SLIST", "STRING", "UNICODE_ESC", "WS", "')'", "','", "']'", 
		"'}'"
	};
	public static final int EOF=-1;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int CD=4;
	public static final int CD_BINARY=5;
	public static final int CD_Car=6;
	public static final int CD_Car_BINARY=7;
	public static final int COMMENT=8;
	public static final int COP=9;
	public static final int CompleteCOP=10;
	public static final int DICT=11;
	public static final int ESC_SEQ=12;
	public static final int HEX_DIGIT=13;
	public static final int ID=14;
	public static final int IdentifierPart=15;
	public static final int IdentifierStart=16;
	public static final int LIST=17;
	public static final int LUCOP=18;
	public static final int LUCarCOP=19;
	public static final int OCTAL_ESC=20;
	public static final int PAIR=21;
	public static final int PD=22;
	public static final int SD_BINARY=23;
	public static final int SD_COMPOUND=24;
	public static final int SD_FALSE=25;
	public static final int SD_NULL=26;
	public static final int SD_PRIMARY=27;
	public static final int SD_UNARY=28;
	public static final int SLIST=29;
	public static final int STRING=30;
	public static final int UNICODE_ESC=31;
	public static final int WS=32;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public SpiderDiagramsParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public SpiderDiagramsParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return SpiderDiagramsParser.tokenNames; }
	@Override public String getGrammarFileName() { return "SpiderDiagrams.g"; }


	@Override
	public void reportError(RecognitionException e) {
	    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
	}


	public static class start_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "start"
	// SpiderDiagrams.g:77:1: start : spiderDiagram ;
	public final SpiderDiagramsParser.start_return start() throws RecognitionException {
		SpiderDiagramsParser.start_return retval = new SpiderDiagramsParser.start_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope spiderDiagram1 =null;


		try {
			// SpiderDiagrams.g:78:5: ( spiderDiagram )
			// SpiderDiagrams.g:78:10: spiderDiagram
			{
			root_0 = (CommonTree)adaptor.nil();


			pushFollow(FOLLOW_spiderDiagram_in_start553);
			spiderDiagram1=spiderDiagram();
			state._fsp--;

			adaptor.addChild(root_0, spiderDiagram1.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "start"


	public static class spiderDiagram_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "spiderDiagram"
	// SpiderDiagrams.g:81:1: spiderDiagram : ( 'PrimarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'UnarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'BinarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CompoundSD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'NullSD' ^ ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)? | 'FalseSD' ^ ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)? | 'COP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'LUCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'LUCarCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CompleteCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'PD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'BinaryCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'BinaryCarCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CarCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !);
	public final SpiderDiagramsParser.spiderDiagram_return spiderDiagram() throws RecognitionException {
		SpiderDiagramsParser.spiderDiagram_return retval = new SpiderDiagramsParser.spiderDiagram_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token string_literal2=null;
		Token char_literal3=null;
		Token char_literal5=null;
		Token char_literal7=null;
		Token string_literal8=null;
		Token char_literal9=null;
		Token char_literal11=null;
		Token char_literal13=null;
		Token string_literal14=null;
		Token char_literal15=null;
		Token char_literal17=null;
		Token char_literal19=null;
		Token string_literal20=null;
		Token char_literal21=null;
		Token char_literal23=null;
		Token char_literal25=null;
		Token string_literal26=null;
		Token char_literal27=null;
		Token char_literal29=null;
		Token char_literal31=null;
		Token string_literal32=null;
		Token char_literal33=null;
		Token char_literal35=null;
		Token char_literal37=null;
		Token string_literal38=null;
		Token char_literal39=null;
		Token char_literal41=null;
		Token char_literal43=null;
		Token string_literal44=null;
		Token char_literal45=null;
		Token char_literal47=null;
		Token char_literal49=null;
		Token string_literal50=null;
		Token char_literal51=null;
		Token char_literal53=null;
		Token char_literal55=null;
		Token string_literal56=null;
		Token char_literal57=null;
		Token char_literal59=null;
		Token char_literal61=null;
		Token string_literal62=null;
		Token char_literal63=null;
		Token char_literal65=null;
		Token char_literal67=null;
		Token string_literal68=null;
		Token char_literal69=null;
		Token char_literal71=null;
		Token char_literal73=null;
		Token string_literal74=null;
		Token char_literal75=null;
		Token char_literal77=null;
		Token char_literal79=null;
		Token string_literal80=null;
		Token char_literal81=null;
		Token char_literal83=null;
		Token char_literal85=null;
		Token string_literal86=null;
		Token char_literal87=null;
		Token char_literal89=null;
		Token char_literal91=null;
		ParserRuleReturnScope keyValue4 =null;
		ParserRuleReturnScope keyValue6 =null;
		ParserRuleReturnScope keyValue10 =null;
		ParserRuleReturnScope keyValue12 =null;
		ParserRuleReturnScope keyValue16 =null;
		ParserRuleReturnScope keyValue18 =null;
		ParserRuleReturnScope keyValue22 =null;
		ParserRuleReturnScope keyValue24 =null;
		ParserRuleReturnScope keyValue28 =null;
		ParserRuleReturnScope keyValue30 =null;
		ParserRuleReturnScope keyValue34 =null;
		ParserRuleReturnScope keyValue36 =null;
		ParserRuleReturnScope keyValue40 =null;
		ParserRuleReturnScope keyValue42 =null;
		ParserRuleReturnScope keyValue46 =null;
		ParserRuleReturnScope keyValue48 =null;
		ParserRuleReturnScope keyValue52 =null;
		ParserRuleReturnScope keyValue54 =null;
		ParserRuleReturnScope keyValue58 =null;
		ParserRuleReturnScope keyValue60 =null;
		ParserRuleReturnScope keyValue64 =null;
		ParserRuleReturnScope keyValue66 =null;
		ParserRuleReturnScope keyValue70 =null;
		ParserRuleReturnScope keyValue72 =null;
		ParserRuleReturnScope keyValue76 =null;
		ParserRuleReturnScope keyValue78 =null;
		ParserRuleReturnScope keyValue82 =null;
		ParserRuleReturnScope keyValue84 =null;
		ParserRuleReturnScope keyValue88 =null;
		ParserRuleReturnScope keyValue90 =null;

		CommonTree string_literal2_tree=null;
		CommonTree char_literal3_tree=null;
		CommonTree char_literal5_tree=null;
		CommonTree char_literal7_tree=null;
		CommonTree string_literal8_tree=null;
		CommonTree char_literal9_tree=null;
		CommonTree char_literal11_tree=null;
		CommonTree char_literal13_tree=null;
		CommonTree string_literal14_tree=null;
		CommonTree char_literal15_tree=null;
		CommonTree char_literal17_tree=null;
		CommonTree char_literal19_tree=null;
		CommonTree string_literal20_tree=null;
		CommonTree char_literal21_tree=null;
		CommonTree char_literal23_tree=null;
		CommonTree char_literal25_tree=null;
		CommonTree string_literal26_tree=null;
		CommonTree char_literal27_tree=null;
		CommonTree char_literal29_tree=null;
		CommonTree char_literal31_tree=null;
		CommonTree string_literal32_tree=null;
		CommonTree char_literal33_tree=null;
		CommonTree char_literal35_tree=null;
		CommonTree char_literal37_tree=null;
		CommonTree string_literal38_tree=null;
		CommonTree char_literal39_tree=null;
		CommonTree char_literal41_tree=null;
		CommonTree char_literal43_tree=null;
		CommonTree string_literal44_tree=null;
		CommonTree char_literal45_tree=null;
		CommonTree char_literal47_tree=null;
		CommonTree char_literal49_tree=null;
		CommonTree string_literal50_tree=null;
		CommonTree char_literal51_tree=null;
		CommonTree char_literal53_tree=null;
		CommonTree char_literal55_tree=null;
		CommonTree string_literal56_tree=null;
		CommonTree char_literal57_tree=null;
		CommonTree char_literal59_tree=null;
		CommonTree char_literal61_tree=null;
		CommonTree string_literal62_tree=null;
		CommonTree char_literal63_tree=null;
		CommonTree char_literal65_tree=null;
		CommonTree char_literal67_tree=null;
		CommonTree string_literal68_tree=null;
		CommonTree char_literal69_tree=null;
		CommonTree char_literal71_tree=null;
		CommonTree char_literal73_tree=null;
		CommonTree string_literal74_tree=null;
		CommonTree char_literal75_tree=null;
		CommonTree char_literal77_tree=null;
		CommonTree char_literal79_tree=null;
		CommonTree string_literal80_tree=null;
		CommonTree char_literal81_tree=null;
		CommonTree char_literal83_tree=null;
		CommonTree char_literal85_tree=null;
		CommonTree string_literal86_tree=null;
		CommonTree char_literal87_tree=null;
		CommonTree char_literal89_tree=null;
		CommonTree char_literal91_tree=null;

		try {
			// SpiderDiagrams.g:82:5: ( 'PrimarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'UnarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'BinarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CompoundSD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'NullSD' ^ ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)? | 'FalseSD' ^ ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)? | 'COP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'LUCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'LUCarCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CompleteCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'PD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'BinaryCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'BinaryCarCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !| 'CarCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)
			int alt33=15;
			switch ( input.LA(1) ) {
			case SD_PRIMARY:
				{
				alt33=1;
				}
				break;
			case SD_UNARY:
				{
				alt33=2;
				}
				break;
			case SD_BINARY:
				{
				alt33=3;
				}
				break;
			case SD_COMPOUND:
				{
				alt33=4;
				}
				break;
			case SD_NULL:
				{
				alt33=5;
				}
				break;
			case SD_FALSE:
				{
				alt33=6;
				}
				break;
			case COP:
				{
				alt33=7;
				}
				break;
			case LUCOP:
				{
				alt33=8;
				}
				break;
			case LUCarCOP:
				{
				alt33=9;
				}
				break;
			case CompleteCOP:
				{
				alt33=10;
				}
				break;
			case PD:
				{
				alt33=11;
				}
				break;
			case CD_BINARY:
				{
				alt33=12;
				}
				break;
			case CD_Car_BINARY:
				{
				alt33=13;
				}
				break;
			case CD:
				{
				alt33=14;
				}
				break;
			case CD_Car:
				{
				alt33=15;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 33, 0, input);
				throw nvae;
			}
			switch (alt33) {
				case 1 :
					// SpiderDiagrams.g:82:10: 'PrimarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal2=(Token)match(input,SD_PRIMARY,FOLLOW_SD_PRIMARY_in_spiderDiagram573); 
					string_literal2_tree = (CommonTree)adaptor.create(string_literal2);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal2_tree, root_0);

					char_literal3=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram576); 
					// SpiderDiagrams.g:82:28: ( keyValue ( ',' ! keyValue )* )?
					int alt2=2;
					int LA2_0 = input.LA(1);
					if ( (LA2_0==ID) ) {
						alt2=1;
					}
					switch (alt2) {
						case 1 :
							// SpiderDiagrams.g:82:29: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram580);
							keyValue4=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue4.getTree());

							// SpiderDiagrams.g:82:38: ( ',' ! keyValue )*
							loop1:
							while (true) {
								int alt1=2;
								int LA1_0 = input.LA(1);
								if ( (LA1_0==34) ) {
									alt1=1;
								}

								switch (alt1) {
								case 1 :
									// SpiderDiagrams.g:82:39: ',' ! keyValue
									{
									char_literal5=(Token)match(input,34,FOLLOW_34_in_spiderDiagram583); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram586);
									keyValue6=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue6.getTree());

									}
									break;

								default :
									break loop1;
								}
							}

							}
							break;

					}

					char_literal7=(Token)match(input,36,FOLLOW_36_in_spiderDiagram592); 
					}
					break;
				case 2 :
					// SpiderDiagrams.g:83:10: 'UnarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal8=(Token)match(input,SD_UNARY,FOLLOW_SD_UNARY_in_spiderDiagram604); 
					string_literal8_tree = (CommonTree)adaptor.create(string_literal8);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal8_tree, root_0);

					char_literal9=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram607); 
					// SpiderDiagrams.g:83:26: ( keyValue ( ',' ! keyValue )* )?
					int alt4=2;
					int LA4_0 = input.LA(1);
					if ( (LA4_0==ID) ) {
						alt4=1;
					}
					switch (alt4) {
						case 1 :
							// SpiderDiagrams.g:83:27: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram611);
							keyValue10=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue10.getTree());

							// SpiderDiagrams.g:83:36: ( ',' ! keyValue )*
							loop3:
							while (true) {
								int alt3=2;
								int LA3_0 = input.LA(1);
								if ( (LA3_0==34) ) {
									alt3=1;
								}

								switch (alt3) {
								case 1 :
									// SpiderDiagrams.g:83:37: ',' ! keyValue
									{
									char_literal11=(Token)match(input,34,FOLLOW_34_in_spiderDiagram614); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram617);
									keyValue12=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue12.getTree());

									}
									break;

								default :
									break loop3;
								}
							}

							}
							break;

					}

					char_literal13=(Token)match(input,36,FOLLOW_36_in_spiderDiagram623); 
					}
					break;
				case 3 :
					// SpiderDiagrams.g:84:10: 'BinarySD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal14=(Token)match(input,SD_BINARY,FOLLOW_SD_BINARY_in_spiderDiagram635); 
					string_literal14_tree = (CommonTree)adaptor.create(string_literal14);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal14_tree, root_0);

					char_literal15=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram638); 
					// SpiderDiagrams.g:84:27: ( keyValue ( ',' ! keyValue )* )?
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( (LA6_0==ID) ) {
						alt6=1;
					}
					switch (alt6) {
						case 1 :
							// SpiderDiagrams.g:84:28: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram642);
							keyValue16=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue16.getTree());

							// SpiderDiagrams.g:84:37: ( ',' ! keyValue )*
							loop5:
							while (true) {
								int alt5=2;
								int LA5_0 = input.LA(1);
								if ( (LA5_0==34) ) {
									alt5=1;
								}

								switch (alt5) {
								case 1 :
									// SpiderDiagrams.g:84:38: ',' ! keyValue
									{
									char_literal17=(Token)match(input,34,FOLLOW_34_in_spiderDiagram645); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram648);
									keyValue18=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue18.getTree());

									}
									break;

								default :
									break loop5;
								}
							}

							}
							break;

					}

					char_literal19=(Token)match(input,36,FOLLOW_36_in_spiderDiagram654); 
					}
					break;
				case 4 :
					// SpiderDiagrams.g:85:10: 'CompoundSD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal20=(Token)match(input,SD_COMPOUND,FOLLOW_SD_COMPOUND_in_spiderDiagram666); 
					string_literal20_tree = (CommonTree)adaptor.create(string_literal20);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal20_tree, root_0);

					char_literal21=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram669); 
					// SpiderDiagrams.g:85:29: ( keyValue ( ',' ! keyValue )* )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==ID) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// SpiderDiagrams.g:85:30: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram673);
							keyValue22=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue22.getTree());

							// SpiderDiagrams.g:85:39: ( ',' ! keyValue )*
							loop7:
							while (true) {
								int alt7=2;
								int LA7_0 = input.LA(1);
								if ( (LA7_0==34) ) {
									alt7=1;
								}

								switch (alt7) {
								case 1 :
									// SpiderDiagrams.g:85:40: ',' ! keyValue
									{
									char_literal23=(Token)match(input,34,FOLLOW_34_in_spiderDiagram676); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram679);
									keyValue24=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue24.getTree());

									}
									break;

								default :
									break loop7;
								}
							}

							}
							break;

					}

					char_literal25=(Token)match(input,36,FOLLOW_36_in_spiderDiagram685); 
					}
					break;
				case 5 :
					// SpiderDiagrams.g:86:10: 'NullSD' ^ ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)?
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal26=(Token)match(input,SD_NULL,FOLLOW_SD_NULL_in_spiderDiagram697); 
					string_literal26_tree = (CommonTree)adaptor.create(string_literal26);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal26_tree, root_0);

					// SpiderDiagrams.g:86:20: ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)?
					int alt11=2;
					int LA11_0 = input.LA(1);
					if ( (LA11_0==DICT) ) {
						alt11=1;
					}
					switch (alt11) {
						case 1 :
							// SpiderDiagrams.g:86:21: '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
							{
							char_literal27=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram701); 
							// SpiderDiagrams.g:86:26: ( keyValue ( ',' ! keyValue )* )?
							int alt10=2;
							int LA10_0 = input.LA(1);
							if ( (LA10_0==ID) ) {
								alt10=1;
							}
							switch (alt10) {
								case 1 :
									// SpiderDiagrams.g:86:27: keyValue ( ',' ! keyValue )*
									{
									pushFollow(FOLLOW_keyValue_in_spiderDiagram705);
									keyValue28=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue28.getTree());

									// SpiderDiagrams.g:86:36: ( ',' ! keyValue )*
									loop9:
									while (true) {
										int alt9=2;
										int LA9_0 = input.LA(1);
										if ( (LA9_0==34) ) {
											alt9=1;
										}

										switch (alt9) {
										case 1 :
											// SpiderDiagrams.g:86:37: ',' ! keyValue
											{
											char_literal29=(Token)match(input,34,FOLLOW_34_in_spiderDiagram708); 
											pushFollow(FOLLOW_keyValue_in_spiderDiagram711);
											keyValue30=keyValue();
											state._fsp--;

											adaptor.addChild(root_0, keyValue30.getTree());

											}
											break;

										default :
											break loop9;
										}
									}

									}
									break;

							}

							char_literal31=(Token)match(input,36,FOLLOW_36_in_spiderDiagram717); 
							}
							break;

					}

					}
					break;
				case 6 :
					// SpiderDiagrams.g:87:10: 'FalseSD' ^ ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)?
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal32=(Token)match(input,SD_FALSE,FOLLOW_SD_FALSE_in_spiderDiagram731); 
					string_literal32_tree = (CommonTree)adaptor.create(string_literal32);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal32_tree, root_0);

					// SpiderDiagrams.g:87:21: ( '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !)?
					int alt14=2;
					int LA14_0 = input.LA(1);
					if ( (LA14_0==DICT) ) {
						alt14=1;
					}
					switch (alt14) {
						case 1 :
							// SpiderDiagrams.g:87:22: '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
							{
							char_literal33=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram735); 
							// SpiderDiagrams.g:87:27: ( keyValue ( ',' ! keyValue )* )?
							int alt13=2;
							int LA13_0 = input.LA(1);
							if ( (LA13_0==ID) ) {
								alt13=1;
							}
							switch (alt13) {
								case 1 :
									// SpiderDiagrams.g:87:28: keyValue ( ',' ! keyValue )*
									{
									pushFollow(FOLLOW_keyValue_in_spiderDiagram739);
									keyValue34=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue34.getTree());

									// SpiderDiagrams.g:87:37: ( ',' ! keyValue )*
									loop12:
									while (true) {
										int alt12=2;
										int LA12_0 = input.LA(1);
										if ( (LA12_0==34) ) {
											alt12=1;
										}

										switch (alt12) {
										case 1 :
											// SpiderDiagrams.g:87:38: ',' ! keyValue
											{
											char_literal35=(Token)match(input,34,FOLLOW_34_in_spiderDiagram742); 
											pushFollow(FOLLOW_keyValue_in_spiderDiagram745);
											keyValue36=keyValue();
											state._fsp--;

											adaptor.addChild(root_0, keyValue36.getTree());

											}
											break;

										default :
											break loop12;
										}
									}

									}
									break;

							}

							char_literal37=(Token)match(input,36,FOLLOW_36_in_spiderDiagram751); 
							}
							break;

					}

					}
					break;
				case 7 :
					// SpiderDiagrams.g:88:10: 'COP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal38=(Token)match(input,COP,FOLLOW_COP_in_spiderDiagram765); 
					string_literal38_tree = (CommonTree)adaptor.create(string_literal38);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal38_tree, root_0);

					char_literal39=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram768); 
					// SpiderDiagrams.g:88:22: ( keyValue ( ',' ! keyValue )* )?
					int alt16=2;
					int LA16_0 = input.LA(1);
					if ( (LA16_0==ID) ) {
						alt16=1;
					}
					switch (alt16) {
						case 1 :
							// SpiderDiagrams.g:88:23: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram772);
							keyValue40=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue40.getTree());

							// SpiderDiagrams.g:88:32: ( ',' ! keyValue )*
							loop15:
							while (true) {
								int alt15=2;
								int LA15_0 = input.LA(1);
								if ( (LA15_0==34) ) {
									alt15=1;
								}

								switch (alt15) {
								case 1 :
									// SpiderDiagrams.g:88:33: ',' ! keyValue
									{
									char_literal41=(Token)match(input,34,FOLLOW_34_in_spiderDiagram775); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram778);
									keyValue42=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue42.getTree());

									}
									break;

								default :
									break loop15;
								}
							}

							}
							break;

					}

					char_literal43=(Token)match(input,36,FOLLOW_36_in_spiderDiagram784); 
					}
					break;
				case 8 :
					// SpiderDiagrams.g:89:10: 'LUCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal44=(Token)match(input,LUCOP,FOLLOW_LUCOP_in_spiderDiagram798); 
					string_literal44_tree = (CommonTree)adaptor.create(string_literal44);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal44_tree, root_0);

					char_literal45=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram801); 
					// SpiderDiagrams.g:89:24: ( keyValue ( ',' ! keyValue )* )?
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( (LA18_0==ID) ) {
						alt18=1;
					}
					switch (alt18) {
						case 1 :
							// SpiderDiagrams.g:89:25: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram805);
							keyValue46=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue46.getTree());

							// SpiderDiagrams.g:89:34: ( ',' ! keyValue )*
							loop17:
							while (true) {
								int alt17=2;
								int LA17_0 = input.LA(1);
								if ( (LA17_0==34) ) {
									alt17=1;
								}

								switch (alt17) {
								case 1 :
									// SpiderDiagrams.g:89:35: ',' ! keyValue
									{
									char_literal47=(Token)match(input,34,FOLLOW_34_in_spiderDiagram808); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram811);
									keyValue48=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue48.getTree());

									}
									break;

								default :
									break loop17;
								}
							}

							}
							break;

					}

					char_literal49=(Token)match(input,36,FOLLOW_36_in_spiderDiagram817); 
					}
					break;
				case 9 :
					// SpiderDiagrams.g:90:10: 'LUCarCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal50=(Token)match(input,LUCarCOP,FOLLOW_LUCarCOP_in_spiderDiagram830); 
					string_literal50_tree = (CommonTree)adaptor.create(string_literal50);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal50_tree, root_0);

					char_literal51=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram833); 
					// SpiderDiagrams.g:90:27: ( keyValue ( ',' ! keyValue )* )?
					int alt20=2;
					int LA20_0 = input.LA(1);
					if ( (LA20_0==ID) ) {
						alt20=1;
					}
					switch (alt20) {
						case 1 :
							// SpiderDiagrams.g:90:28: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram837);
							keyValue52=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue52.getTree());

							// SpiderDiagrams.g:90:37: ( ',' ! keyValue )*
							loop19:
							while (true) {
								int alt19=2;
								int LA19_0 = input.LA(1);
								if ( (LA19_0==34) ) {
									alt19=1;
								}

								switch (alt19) {
								case 1 :
									// SpiderDiagrams.g:90:38: ',' ! keyValue
									{
									char_literal53=(Token)match(input,34,FOLLOW_34_in_spiderDiagram840); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram843);
									keyValue54=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue54.getTree());

									}
									break;

								default :
									break loop19;
								}
							}

							}
							break;

					}

					char_literal55=(Token)match(input,36,FOLLOW_36_in_spiderDiagram849); 
					}
					break;
				case 10 :
					// SpiderDiagrams.g:91:10: 'CompleteCOP' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal56=(Token)match(input,CompleteCOP,FOLLOW_CompleteCOP_in_spiderDiagram861); 
					string_literal56_tree = (CommonTree)adaptor.create(string_literal56);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal56_tree, root_0);

					char_literal57=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram864); 
					// SpiderDiagrams.g:91:30: ( keyValue ( ',' ! keyValue )* )?
					int alt22=2;
					int LA22_0 = input.LA(1);
					if ( (LA22_0==ID) ) {
						alt22=1;
					}
					switch (alt22) {
						case 1 :
							// SpiderDiagrams.g:91:31: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram868);
							keyValue58=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue58.getTree());

							// SpiderDiagrams.g:91:40: ( ',' ! keyValue )*
							loop21:
							while (true) {
								int alt21=2;
								int LA21_0 = input.LA(1);
								if ( (LA21_0==34) ) {
									alt21=1;
								}

								switch (alt21) {
								case 1 :
									// SpiderDiagrams.g:91:41: ',' ! keyValue
									{
									char_literal59=(Token)match(input,34,FOLLOW_34_in_spiderDiagram871); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram874);
									keyValue60=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue60.getTree());

									}
									break;

								default :
									break loop21;
								}
							}

							}
							break;

					}

					char_literal61=(Token)match(input,36,FOLLOW_36_in_spiderDiagram880); 
					}
					break;
				case 11 :
					// SpiderDiagrams.g:92:10: 'PD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal62=(Token)match(input,PD,FOLLOW_PD_in_spiderDiagram892); 
					string_literal62_tree = (CommonTree)adaptor.create(string_literal62);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal62_tree, root_0);

					char_literal63=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram895); 
					// SpiderDiagrams.g:92:21: ( keyValue ( ',' ! keyValue )* )?
					int alt24=2;
					int LA24_0 = input.LA(1);
					if ( (LA24_0==ID) ) {
						alt24=1;
					}
					switch (alt24) {
						case 1 :
							// SpiderDiagrams.g:92:22: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram899);
							keyValue64=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue64.getTree());

							// SpiderDiagrams.g:92:31: ( ',' ! keyValue )*
							loop23:
							while (true) {
								int alt23=2;
								int LA23_0 = input.LA(1);
								if ( (LA23_0==34) ) {
									alt23=1;
								}

								switch (alt23) {
								case 1 :
									// SpiderDiagrams.g:92:32: ',' ! keyValue
									{
									char_literal65=(Token)match(input,34,FOLLOW_34_in_spiderDiagram902); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram905);
									keyValue66=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue66.getTree());

									}
									break;

								default :
									break loop23;
								}
							}

							}
							break;

					}

					char_literal67=(Token)match(input,36,FOLLOW_36_in_spiderDiagram911); 
					}
					break;
				case 12 :
					// SpiderDiagrams.g:93:10: 'BinaryCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal68=(Token)match(input,CD_BINARY,FOLLOW_CD_BINARY_in_spiderDiagram924); 
					string_literal68_tree = (CommonTree)adaptor.create(string_literal68);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal68_tree, root_0);

					char_literal69=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram927); 
					// SpiderDiagrams.g:93:27: ( keyValue ( ',' ! keyValue )* )?
					int alt26=2;
					int LA26_0 = input.LA(1);
					if ( (LA26_0==ID) ) {
						alt26=1;
					}
					switch (alt26) {
						case 1 :
							// SpiderDiagrams.g:93:28: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram931);
							keyValue70=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue70.getTree());

							// SpiderDiagrams.g:93:37: ( ',' ! keyValue )*
							loop25:
							while (true) {
								int alt25=2;
								int LA25_0 = input.LA(1);
								if ( (LA25_0==34) ) {
									alt25=1;
								}

								switch (alt25) {
								case 1 :
									// SpiderDiagrams.g:93:38: ',' ! keyValue
									{
									char_literal71=(Token)match(input,34,FOLLOW_34_in_spiderDiagram934); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram937);
									keyValue72=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue72.getTree());

									}
									break;

								default :
									break loop25;
								}
							}

							}
							break;

					}

					char_literal73=(Token)match(input,36,FOLLOW_36_in_spiderDiagram943); 
					}
					break;
				case 13 :
					// SpiderDiagrams.g:94:10: 'BinaryCarCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal74=(Token)match(input,CD_Car_BINARY,FOLLOW_CD_Car_BINARY_in_spiderDiagram955); 
					string_literal74_tree = (CommonTree)adaptor.create(string_literal74);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal74_tree, root_0);

					char_literal75=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram958); 
					// SpiderDiagrams.g:94:30: ( keyValue ( ',' ! keyValue )* )?
					int alt28=2;
					int LA28_0 = input.LA(1);
					if ( (LA28_0==ID) ) {
						alt28=1;
					}
					switch (alt28) {
						case 1 :
							// SpiderDiagrams.g:94:31: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram962);
							keyValue76=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue76.getTree());

							// SpiderDiagrams.g:94:40: ( ',' ! keyValue )*
							loop27:
							while (true) {
								int alt27=2;
								int LA27_0 = input.LA(1);
								if ( (LA27_0==34) ) {
									alt27=1;
								}

								switch (alt27) {
								case 1 :
									// SpiderDiagrams.g:94:41: ',' ! keyValue
									{
									char_literal77=(Token)match(input,34,FOLLOW_34_in_spiderDiagram965); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram968);
									keyValue78=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue78.getTree());

									}
									break;

								default :
									break loop27;
								}
							}

							}
							break;

					}

					char_literal79=(Token)match(input,36,FOLLOW_36_in_spiderDiagram974); 
					}
					break;
				case 14 :
					// SpiderDiagrams.g:95:10: 'CD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal80=(Token)match(input,CD,FOLLOW_CD_in_spiderDiagram986); 
					string_literal80_tree = (CommonTree)adaptor.create(string_literal80);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal80_tree, root_0);

					char_literal81=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram989); 
					// SpiderDiagrams.g:95:21: ( keyValue ( ',' ! keyValue )* )?
					int alt30=2;
					int LA30_0 = input.LA(1);
					if ( (LA30_0==ID) ) {
						alt30=1;
					}
					switch (alt30) {
						case 1 :
							// SpiderDiagrams.g:95:22: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram993);
							keyValue82=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue82.getTree());

							// SpiderDiagrams.g:95:31: ( ',' ! keyValue )*
							loop29:
							while (true) {
								int alt29=2;
								int LA29_0 = input.LA(1);
								if ( (LA29_0==34) ) {
									alt29=1;
								}

								switch (alt29) {
								case 1 :
									// SpiderDiagrams.g:95:32: ',' ! keyValue
									{
									char_literal83=(Token)match(input,34,FOLLOW_34_in_spiderDiagram996); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram999);
									keyValue84=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue84.getTree());

									}
									break;

								default :
									break loop29;
								}
							}

							}
							break;

					}

					char_literal85=(Token)match(input,36,FOLLOW_36_in_spiderDiagram1005); 
					}
					break;
				case 15 :
					// SpiderDiagrams.g:96:10: 'CarCD' ^ '{' ! ( keyValue ( ',' ! keyValue )* )? '}' !
					{
					root_0 = (CommonTree)adaptor.nil();


					string_literal86=(Token)match(input,CD_Car,FOLLOW_CD_Car_in_spiderDiagram1017); 
					string_literal86_tree = (CommonTree)adaptor.create(string_literal86);
					root_0 = (CommonTree)adaptor.becomeRoot(string_literal86_tree, root_0);

					char_literal87=(Token)match(input,DICT,FOLLOW_DICT_in_spiderDiagram1020); 
					// SpiderDiagrams.g:96:24: ( keyValue ( ',' ! keyValue )* )?
					int alt32=2;
					int LA32_0 = input.LA(1);
					if ( (LA32_0==ID) ) {
						alt32=1;
					}
					switch (alt32) {
						case 1 :
							// SpiderDiagrams.g:96:25: keyValue ( ',' ! keyValue )*
							{
							pushFollow(FOLLOW_keyValue_in_spiderDiagram1024);
							keyValue88=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue88.getTree());

							// SpiderDiagrams.g:96:34: ( ',' ! keyValue )*
							loop31:
							while (true) {
								int alt31=2;
								int LA31_0 = input.LA(1);
								if ( (LA31_0==34) ) {
									alt31=1;
								}

								switch (alt31) {
								case 1 :
									// SpiderDiagrams.g:96:35: ',' ! keyValue
									{
									char_literal89=(Token)match(input,34,FOLLOW_34_in_spiderDiagram1027); 
									pushFollow(FOLLOW_keyValue_in_spiderDiagram1030);
									keyValue90=keyValue();
									state._fsp--;

									adaptor.addChild(root_0, keyValue90.getTree());

									}
									break;

								default :
									break loop31;
								}
							}

							}
							break;

					}

					char_literal91=(Token)match(input,36,FOLLOW_36_in_spiderDiagram1036); 
					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "spiderDiagram"


	public static class keyValues_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "keyValues"
	// SpiderDiagrams.g:99:1: keyValues : '{' ^ ( keyValue ( ',' ! keyValue )* )? '}' !;
	public final SpiderDiagramsParser.keyValues_return keyValues() throws RecognitionException {
		SpiderDiagramsParser.keyValues_return retval = new SpiderDiagramsParser.keyValues_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal92=null;
		Token char_literal94=null;
		Token char_literal96=null;
		ParserRuleReturnScope keyValue93 =null;
		ParserRuleReturnScope keyValue95 =null;

		CommonTree char_literal92_tree=null;
		CommonTree char_literal94_tree=null;
		CommonTree char_literal96_tree=null;

		try {
			// SpiderDiagrams.g:100:5: ( '{' ^ ( keyValue ( ',' ! keyValue )* )? '}' !)
			// SpiderDiagrams.g:100:10: '{' ^ ( keyValue ( ',' ! keyValue )* )? '}' !
			{
			root_0 = (CommonTree)adaptor.nil();


			char_literal92=(Token)match(input,DICT,FOLLOW_DICT_in_keyValues1057); 
			char_literal92_tree = (CommonTree)adaptor.create(char_literal92);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal92_tree, root_0);

			// SpiderDiagrams.g:100:15: ( keyValue ( ',' ! keyValue )* )?
			int alt35=2;
			int LA35_0 = input.LA(1);
			if ( (LA35_0==ID) ) {
				alt35=1;
			}
			switch (alt35) {
				case 1 :
					// SpiderDiagrams.g:100:16: keyValue ( ',' ! keyValue )*
					{
					pushFollow(FOLLOW_keyValue_in_keyValues1061);
					keyValue93=keyValue();
					state._fsp--;

					adaptor.addChild(root_0, keyValue93.getTree());

					// SpiderDiagrams.g:100:25: ( ',' ! keyValue )*
					loop34:
					while (true) {
						int alt34=2;
						int LA34_0 = input.LA(1);
						if ( (LA34_0==34) ) {
							alt34=1;
						}

						switch (alt34) {
						case 1 :
							// SpiderDiagrams.g:100:26: ',' ! keyValue
							{
							char_literal94=(Token)match(input,34,FOLLOW_34_in_keyValues1064); 
							pushFollow(FOLLOW_keyValue_in_keyValues1067);
							keyValue95=keyValue();
							state._fsp--;

							adaptor.addChild(root_0, keyValue95.getTree());

							}
							break;

						default :
							break loop34;
						}
					}

					}
					break;

			}

			char_literal96=(Token)match(input,36,FOLLOW_36_in_keyValues1073); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "keyValues"


	public static class list_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "list"
	// SpiderDiagrams.g:103:1: list : '[' ^ ( languageElement ( ',' ! languageElement )* )? ']' !;
	public final SpiderDiagramsParser.list_return list() throws RecognitionException {
		SpiderDiagramsParser.list_return retval = new SpiderDiagramsParser.list_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal97=null;
		Token char_literal99=null;
		Token char_literal101=null;
		ParserRuleReturnScope languageElement98 =null;
		ParserRuleReturnScope languageElement100 =null;

		CommonTree char_literal97_tree=null;
		CommonTree char_literal99_tree=null;
		CommonTree char_literal101_tree=null;

		try {
			// SpiderDiagrams.g:104:5: ( '[' ^ ( languageElement ( ',' ! languageElement )* )? ']' !)
			// SpiderDiagrams.g:104:10: '[' ^ ( languageElement ( ',' ! languageElement )* )? ']' !
			{
			root_0 = (CommonTree)adaptor.nil();


			char_literal97=(Token)match(input,LIST,FOLLOW_LIST_in_list1094); 
			char_literal97_tree = (CommonTree)adaptor.create(char_literal97);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal97_tree, root_0);

			// SpiderDiagrams.g:104:15: ( languageElement ( ',' ! languageElement )* )?
			int alt37=2;
			int LA37_0 = input.LA(1);
			if ( ((LA37_0 >= CD && LA37_0 <= CD_Car_BINARY)||(LA37_0 >= COP && LA37_0 <= DICT)||(LA37_0 >= LIST && LA37_0 <= LUCarCOP)||(LA37_0 >= PD && LA37_0 <= STRING)) ) {
				alt37=1;
			}
			switch (alt37) {
				case 1 :
					// SpiderDiagrams.g:104:16: languageElement ( ',' ! languageElement )*
					{
					pushFollow(FOLLOW_languageElement_in_list1098);
					languageElement98=languageElement();
					state._fsp--;

					adaptor.addChild(root_0, languageElement98.getTree());

					// SpiderDiagrams.g:104:32: ( ',' ! languageElement )*
					loop36:
					while (true) {
						int alt36=2;
						int LA36_0 = input.LA(1);
						if ( (LA36_0==34) ) {
							alt36=1;
						}

						switch (alt36) {
						case 1 :
							// SpiderDiagrams.g:104:33: ',' ! languageElement
							{
							char_literal99=(Token)match(input,34,FOLLOW_34_in_list1101); 
							pushFollow(FOLLOW_languageElement_in_list1104);
							languageElement100=languageElement();
							state._fsp--;

							adaptor.addChild(root_0, languageElement100.getTree());

							}
							break;

						default :
							break loop36;
						}
					}

					}
					break;

			}

			char_literal101=(Token)match(input,35,FOLLOW_35_in_list1110); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "list"


	public static class sortedList_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "sortedList"
	// SpiderDiagrams.g:107:1: sortedList : '(' ^ ( languageElement ( ',' ! languageElement )* )? ')' !;
	public final SpiderDiagramsParser.sortedList_return sortedList() throws RecognitionException {
		SpiderDiagramsParser.sortedList_return retval = new SpiderDiagramsParser.sortedList_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal102=null;
		Token char_literal104=null;
		Token char_literal106=null;
		ParserRuleReturnScope languageElement103 =null;
		ParserRuleReturnScope languageElement105 =null;

		CommonTree char_literal102_tree=null;
		CommonTree char_literal104_tree=null;
		CommonTree char_literal106_tree=null;

		try {
			// SpiderDiagrams.g:108:5: ( '(' ^ ( languageElement ( ',' ! languageElement )* )? ')' !)
			// SpiderDiagrams.g:108:10: '(' ^ ( languageElement ( ',' ! languageElement )* )? ')' !
			{
			root_0 = (CommonTree)adaptor.nil();


			char_literal102=(Token)match(input,SLIST,FOLLOW_SLIST_in_sortedList1135); 
			char_literal102_tree = (CommonTree)adaptor.create(char_literal102);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal102_tree, root_0);

			// SpiderDiagrams.g:108:15: ( languageElement ( ',' ! languageElement )* )?
			int alt39=2;
			int LA39_0 = input.LA(1);
			if ( ((LA39_0 >= CD && LA39_0 <= CD_Car_BINARY)||(LA39_0 >= COP && LA39_0 <= DICT)||(LA39_0 >= LIST && LA39_0 <= LUCarCOP)||(LA39_0 >= PD && LA39_0 <= STRING)) ) {
				alt39=1;
			}
			switch (alt39) {
				case 1 :
					// SpiderDiagrams.g:108:16: languageElement ( ',' ! languageElement )*
					{
					pushFollow(FOLLOW_languageElement_in_sortedList1139);
					languageElement103=languageElement();
					state._fsp--;

					adaptor.addChild(root_0, languageElement103.getTree());

					// SpiderDiagrams.g:108:32: ( ',' ! languageElement )*
					loop38:
					while (true) {
						int alt38=2;
						int LA38_0 = input.LA(1);
						if ( (LA38_0==34) ) {
							alt38=1;
						}

						switch (alt38) {
						case 1 :
							// SpiderDiagrams.g:108:33: ',' ! languageElement
							{
							char_literal104=(Token)match(input,34,FOLLOW_34_in_sortedList1142); 
							pushFollow(FOLLOW_languageElement_in_sortedList1145);
							languageElement105=languageElement();
							state._fsp--;

							adaptor.addChild(root_0, languageElement105.getTree());

							}
							break;

						default :
							break loop38;
						}
					}

					}
					break;

			}

			char_literal106=(Token)match(input,33,FOLLOW_33_in_sortedList1151); 
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "sortedList"


	public static class keyValue_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "keyValue"
	// SpiderDiagrams.g:111:1: keyValue : ID '=' ^ languageElement ;
	public final SpiderDiagramsParser.keyValue_return keyValue() throws RecognitionException {
		SpiderDiagramsParser.keyValue_return retval = new SpiderDiagramsParser.keyValue_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token ID107=null;
		Token char_literal108=null;
		ParserRuleReturnScope languageElement109 =null;

		CommonTree ID107_tree=null;
		CommonTree char_literal108_tree=null;

		try {
			// SpiderDiagrams.g:112:5: ( ID '=' ^ languageElement )
			// SpiderDiagrams.g:112:10: ID '=' ^ languageElement
			{
			root_0 = (CommonTree)adaptor.nil();


			ID107=(Token)match(input,ID,FOLLOW_ID_in_keyValue1172); 
			ID107_tree = (CommonTree)adaptor.create(ID107);
			adaptor.addChild(root_0, ID107_tree);

			char_literal108=(Token)match(input,PAIR,FOLLOW_PAIR_in_keyValue1174); 
			char_literal108_tree = (CommonTree)adaptor.create(char_literal108);
			root_0 = (CommonTree)adaptor.becomeRoot(char_literal108_tree, root_0);

			pushFollow(FOLLOW_languageElement_in_keyValue1177);
			languageElement109=languageElement();
			state._fsp--;

			adaptor.addChild(root_0, languageElement109.getTree());

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "keyValue"


	public static class languageElement_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "languageElement"
	// SpiderDiagrams.g:115:1: languageElement : ( STRING | keyValues | list | sortedList | spiderDiagram );
	public final SpiderDiagramsParser.languageElement_return languageElement() throws RecognitionException {
		SpiderDiagramsParser.languageElement_return retval = new SpiderDiagramsParser.languageElement_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token STRING110=null;
		ParserRuleReturnScope keyValues111 =null;
		ParserRuleReturnScope list112 =null;
		ParserRuleReturnScope sortedList113 =null;
		ParserRuleReturnScope spiderDiagram114 =null;

		CommonTree STRING110_tree=null;

		try {
			// SpiderDiagrams.g:116:5: ( STRING | keyValues | list | sortedList | spiderDiagram )
			int alt40=5;
			switch ( input.LA(1) ) {
			case STRING:
				{
				alt40=1;
				}
				break;
			case DICT:
				{
				alt40=2;
				}
				break;
			case LIST:
				{
				alt40=3;
				}
				break;
			case SLIST:
				{
				alt40=4;
				}
				break;
			case CD:
			case CD_BINARY:
			case CD_Car:
			case CD_Car_BINARY:
			case COP:
			case CompleteCOP:
			case LUCOP:
			case LUCarCOP:
			case PD:
			case SD_BINARY:
			case SD_COMPOUND:
			case SD_FALSE:
			case SD_NULL:
			case SD_PRIMARY:
			case SD_UNARY:
				{
				alt40=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 40, 0, input);
				throw nvae;
			}
			switch (alt40) {
				case 1 :
					// SpiderDiagrams.g:116:10: STRING
					{
					root_0 = (CommonTree)adaptor.nil();


					STRING110=(Token)match(input,STRING,FOLLOW_STRING_in_languageElement1197); 
					STRING110_tree = (CommonTree)adaptor.create(STRING110);
					adaptor.addChild(root_0, STRING110_tree);

					}
					break;
				case 2 :
					// SpiderDiagrams.g:117:10: keyValues
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_keyValues_in_languageElement1208);
					keyValues111=keyValues();
					state._fsp--;

					adaptor.addChild(root_0, keyValues111.getTree());

					}
					break;
				case 3 :
					// SpiderDiagrams.g:118:10: list
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_list_in_languageElement1219);
					list112=list();
					state._fsp--;

					adaptor.addChild(root_0, list112.getTree());

					}
					break;
				case 4 :
					// SpiderDiagrams.g:119:10: sortedList
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_sortedList_in_languageElement1230);
					sortedList113=sortedList();
					state._fsp--;

					adaptor.addChild(root_0, sortedList113.getTree());

					}
					break;
				case 5 :
					// SpiderDiagrams.g:120:10: spiderDiagram
					{
					root_0 = (CommonTree)adaptor.nil();


					pushFollow(FOLLOW_spiderDiagram_in_languageElement1241);
					spiderDiagram114=spiderDiagram();
					state._fsp--;

					adaptor.addChild(root_0, spiderDiagram114.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}

		catch (RecognitionException e) {
		    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "languageElement"

	// Delegated rules



	public static final BitSet FOLLOW_spiderDiagram_in_start553 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SD_PRIMARY_in_spiderDiagram573 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram576 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram580 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram583 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram586 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram592 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SD_UNARY_in_spiderDiagram604 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram607 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram611 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram614 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram617 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram623 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SD_BINARY_in_spiderDiagram635 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram638 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram642 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram645 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram648 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram654 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SD_COMPOUND_in_spiderDiagram666 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram669 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram673 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram676 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram679 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram685 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SD_NULL_in_spiderDiagram697 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram701 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram705 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram708 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram711 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram717 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SD_FALSE_in_spiderDiagram731 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram735 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram739 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram742 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram745 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram751 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COP_in_spiderDiagram765 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram768 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram772 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram775 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram778 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram784 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LUCOP_in_spiderDiagram798 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram801 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram805 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram808 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram811 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram817 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LUCarCOP_in_spiderDiagram830 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram833 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram837 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram840 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram843 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram849 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CompleteCOP_in_spiderDiagram861 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram864 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram868 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram871 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram874 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram880 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PD_in_spiderDiagram892 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram895 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram899 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram902 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram905 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram911 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CD_BINARY_in_spiderDiagram924 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram927 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram931 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram934 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram937 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram943 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CD_Car_BINARY_in_spiderDiagram955 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram958 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram962 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram965 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram968 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram974 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CD_in_spiderDiagram986 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram989 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram993 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram996 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram999 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram1005 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CD_Car_in_spiderDiagram1017 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_DICT_in_spiderDiagram1020 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram1024 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_spiderDiagram1027 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_spiderDiagram1030 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_spiderDiagram1036 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DICT_in_keyValues1057 = new BitSet(new long[]{0x0000001000004000L});
	public static final BitSet FOLLOW_keyValue_in_keyValues1061 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_34_in_keyValues1064 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_keyValue_in_keyValues1067 = new BitSet(new long[]{0x0000001400000000L});
	public static final BitSet FOLLOW_36_in_keyValues1073 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LIST_in_list1094 = new BitSet(new long[]{0x000000087FCE0EF0L});
	public static final BitSet FOLLOW_languageElement_in_list1098 = new BitSet(new long[]{0x0000000C00000000L});
	public static final BitSet FOLLOW_34_in_list1101 = new BitSet(new long[]{0x000000007FCE0EF0L});
	public static final BitSet FOLLOW_languageElement_in_list1104 = new BitSet(new long[]{0x0000000C00000000L});
	public static final BitSet FOLLOW_35_in_list1110 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_SLIST_in_sortedList1135 = new BitSet(new long[]{0x000000027FCE0EF0L});
	public static final BitSet FOLLOW_languageElement_in_sortedList1139 = new BitSet(new long[]{0x0000000600000000L});
	public static final BitSet FOLLOW_34_in_sortedList1142 = new BitSet(new long[]{0x000000007FCE0EF0L});
	public static final BitSet FOLLOW_languageElement_in_sortedList1145 = new BitSet(new long[]{0x0000000600000000L});
	public static final BitSet FOLLOW_33_in_sortedList1151 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_keyValue1172 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_PAIR_in_keyValue1174 = new BitSet(new long[]{0x000000007FCE0EF0L});
	public static final BitSet FOLLOW_languageElement_in_keyValue1177 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_languageElement1197 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keyValues_in_languageElement1208 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_list_in_languageElement1219 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_sortedList_in_languageElement1230 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_spiderDiagram_in_languageElement1241 = new BitSet(new long[]{0x0000000000000002L});
}

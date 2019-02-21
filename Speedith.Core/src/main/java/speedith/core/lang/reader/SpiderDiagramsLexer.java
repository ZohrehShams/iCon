// $ANTLR 3.5.2 SpiderDiagrams.g 2019-02-21 18:43:41

package speedith.core.lang.reader;
import static speedith.core.i18n.Translations.i18n;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SpiderDiagramsLexer extends Lexer {
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

	@Override
	public void reportError(RecognitionException e) {
	    throw new ParseException(i18n("ERR_PARSE_INVALID_SYNTAX"), e, this);
	}


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public SpiderDiagramsLexer() {} 
	public SpiderDiagramsLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public SpiderDiagramsLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "SpiderDiagrams.g"; }

	// $ANTLR start "CD"
	public final void mCD() throws RecognitionException {
		try {
			int _type = CD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:18:4: ( 'CD' )
			// SpiderDiagrams.g:18:6: 'CD'
			{
			match("CD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CD"

	// $ANTLR start "CD_BINARY"
	public final void mCD_BINARY() throws RecognitionException {
		try {
			int _type = CD_BINARY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:19:11: ( 'BinaryCD' )
			// SpiderDiagrams.g:19:13: 'BinaryCD'
			{
			match("BinaryCD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CD_BINARY"

	// $ANTLR start "CD_Car"
	public final void mCD_Car() throws RecognitionException {
		try {
			int _type = CD_Car;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:20:8: ( 'CarCD' )
			// SpiderDiagrams.g:20:10: 'CarCD'
			{
			match("CarCD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CD_Car"

	// $ANTLR start "CD_Car_BINARY"
	public final void mCD_Car_BINARY() throws RecognitionException {
		try {
			int _type = CD_Car_BINARY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:21:15: ( 'BinaryCarCD' )
			// SpiderDiagrams.g:21:17: 'BinaryCarCD'
			{
			match("BinaryCarCD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CD_Car_BINARY"

	// $ANTLR start "COP"
	public final void mCOP() throws RecognitionException {
		try {
			int _type = COP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:22:5: ( 'COP' )
			// SpiderDiagrams.g:22:7: 'COP'
			{
			match("COP"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COP"

	// $ANTLR start "CompleteCOP"
	public final void mCompleteCOP() throws RecognitionException {
		try {
			int _type = CompleteCOP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:23:13: ( 'CompleteCOP' )
			// SpiderDiagrams.g:23:15: 'CompleteCOP'
			{
			match("CompleteCOP"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CompleteCOP"

	// $ANTLR start "DICT"
	public final void mDICT() throws RecognitionException {
		try {
			int _type = DICT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:24:6: ( '{' )
			// SpiderDiagrams.g:24:8: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DICT"

	// $ANTLR start "LIST"
	public final void mLIST() throws RecognitionException {
		try {
			int _type = LIST;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:25:6: ( '[' )
			// SpiderDiagrams.g:25:8: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LIST"

	// $ANTLR start "LUCOP"
	public final void mLUCOP() throws RecognitionException {
		try {
			int _type = LUCOP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:26:7: ( 'LUCOP' )
			// SpiderDiagrams.g:26:9: 'LUCOP'
			{
			match("LUCOP"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LUCOP"

	// $ANTLR start "LUCarCOP"
	public final void mLUCarCOP() throws RecognitionException {
		try {
			int _type = LUCarCOP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:27:10: ( 'LUCarCOP' )
			// SpiderDiagrams.g:27:12: 'LUCarCOP'
			{
			match("LUCarCOP"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LUCarCOP"

	// $ANTLR start "PAIR"
	public final void mPAIR() throws RecognitionException {
		try {
			int _type = PAIR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:28:6: ( '=' )
			// SpiderDiagrams.g:28:8: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PAIR"

	// $ANTLR start "PD"
	public final void mPD() throws RecognitionException {
		try {
			int _type = PD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:29:4: ( 'PD' )
			// SpiderDiagrams.g:29:6: 'PD'
			{
			match("PD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "PD"

	// $ANTLR start "SD_BINARY"
	public final void mSD_BINARY() throws RecognitionException {
		try {
			int _type = SD_BINARY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:30:11: ( 'BinarySD' )
			// SpiderDiagrams.g:30:13: 'BinarySD'
			{
			match("BinarySD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SD_BINARY"

	// $ANTLR start "SD_COMPOUND"
	public final void mSD_COMPOUND() throws RecognitionException {
		try {
			int _type = SD_COMPOUND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:31:13: ( 'CompoundSD' )
			// SpiderDiagrams.g:31:15: 'CompoundSD'
			{
			match("CompoundSD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SD_COMPOUND"

	// $ANTLR start "SD_FALSE"
	public final void mSD_FALSE() throws RecognitionException {
		try {
			int _type = SD_FALSE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:32:10: ( 'FalseSD' )
			// SpiderDiagrams.g:32:12: 'FalseSD'
			{
			match("FalseSD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SD_FALSE"

	// $ANTLR start "SD_NULL"
	public final void mSD_NULL() throws RecognitionException {
		try {
			int _type = SD_NULL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:33:9: ( 'NullSD' )
			// SpiderDiagrams.g:33:11: 'NullSD'
			{
			match("NullSD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SD_NULL"

	// $ANTLR start "SD_PRIMARY"
	public final void mSD_PRIMARY() throws RecognitionException {
		try {
			int _type = SD_PRIMARY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:34:12: ( 'PrimarySD' )
			// SpiderDiagrams.g:34:14: 'PrimarySD'
			{
			match("PrimarySD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SD_PRIMARY"

	// $ANTLR start "SD_UNARY"
	public final void mSD_UNARY() throws RecognitionException {
		try {
			int _type = SD_UNARY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:35:10: ( 'UnarySD' )
			// SpiderDiagrams.g:35:12: 'UnarySD'
			{
			match("UnarySD"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SD_UNARY"

	// $ANTLR start "SLIST"
	public final void mSLIST() throws RecognitionException {
		try {
			int _type = SLIST;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:36:7: ( '(' )
			// SpiderDiagrams.g:36:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SLIST"

	// $ANTLR start "T__33"
	public final void mT__33() throws RecognitionException {
		try {
			int _type = T__33;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:37:7: ( ')' )
			// SpiderDiagrams.g:37:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__33"

	// $ANTLR start "T__34"
	public final void mT__34() throws RecognitionException {
		try {
			int _type = T__34;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:38:7: ( ',' )
			// SpiderDiagrams.g:38:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__34"

	// $ANTLR start "T__35"
	public final void mT__35() throws RecognitionException {
		try {
			int _type = T__35;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:39:7: ( ']' )
			// SpiderDiagrams.g:39:9: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__35"

	// $ANTLR start "T__36"
	public final void mT__36() throws RecognitionException {
		try {
			int _type = T__36;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:40:7: ( '}' )
			// SpiderDiagrams.g:40:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__36"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:134:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0=='/') ) {
				int LA4_1 = input.LA(2);
				if ( (LA4_1=='/') ) {
					alt4=1;
				}
				else if ( (LA4_1=='*') ) {
					alt4=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// SpiderDiagrams.g:134:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
					{
					match("//"); 

					// SpiderDiagrams.g:134:14: (~ ( '\\n' | '\\r' ) )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\f')||(LA1_0 >= '\u000E' && LA1_0 <= '\uFFFF')) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// SpiderDiagrams.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop1;
						}
					}

					// SpiderDiagrams.g:134:28: ( '\\r' )?
					int alt2=2;
					int LA2_0 = input.LA(1);
					if ( (LA2_0=='\r') ) {
						alt2=1;
					}
					switch (alt2) {
						case 1 :
							// SpiderDiagrams.g:134:28: '\\r'
							{
							match('\r'); 
							}
							break;

					}

					match('\n'); 
					_channel=HIDDEN;
					}
					break;
				case 2 :
					// SpiderDiagrams.g:135:9: '/*' ( options {greedy=false; } : . )* '*/'
					{
					match("/*"); 

					// SpiderDiagrams.g:135:14: ( options {greedy=false; } : . )*
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( (LA3_0=='*') ) {
							int LA3_1 = input.LA(2);
							if ( (LA3_1=='/') ) {
								alt3=2;
							}
							else if ( ((LA3_1 >= '\u0000' && LA3_1 <= '.')||(LA3_1 >= '0' && LA3_1 <= '\uFFFF')) ) {
								alt3=1;
							}

						}
						else if ( ((LA3_0 >= '\u0000' && LA3_0 <= ')')||(LA3_0 >= '+' && LA3_0 <= '\uFFFF')) ) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// SpiderDiagrams.g:135:42: .
							{
							matchAny(); 
							}
							break;

						default :
							break loop3;
						}
					}

					match("*/"); 

					_channel=HIDDEN;
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:134:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
			// SpiderDiagrams.g:134:9: ( ' ' | '\\t' | '\\r' | '\\n' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException {
		try {
			int _type = STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:142:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
			// SpiderDiagrams.g:142:10: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
			{
			match('\"'); 
			// SpiderDiagrams.g:142:14: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
			loop5:
			while (true) {
				int alt5=3;
				int LA5_0 = input.LA(1);
				if ( (LA5_0=='\\') ) {
					alt5=1;
				}
				else if ( ((LA5_0 >= '\u0000' && LA5_0 <= '!')||(LA5_0 >= '#' && LA5_0 <= '[')||(LA5_0 >= ']' && LA5_0 <= '\uFFFF')) ) {
					alt5=2;
				}

				switch (alt5) {
				case 1 :
					// SpiderDiagrams.g:142:16: ESC_SEQ
					{
					mESC_SEQ(); 

					}
					break;
				case 2 :
					// SpiderDiagrams.g:142:26: ~ ( '\\\\' | '\"' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop5;
				}
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING"

	// $ANTLR start "HEX_DIGIT"
	public final void mHEX_DIGIT() throws RecognitionException {
		try {
			// SpiderDiagrams.g:148:5: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
			// SpiderDiagrams.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEX_DIGIT"

	// $ANTLR start "ESC_SEQ"
	public final void mESC_SEQ() throws RecognitionException {
		try {
			// SpiderDiagrams.g:153:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
			int alt6=3;
			int LA6_0 = input.LA(1);
			if ( (LA6_0=='\\') ) {
				switch ( input.LA(2) ) {
				case '\"':
				case '\'':
				case '\\':
				case 'b':
				case 'f':
				case 'n':
				case 'r':
				case 't':
					{
					alt6=1;
					}
					break;
				case 'u':
					{
					alt6=2;
					}
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					{
					alt6=3;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// SpiderDiagrams.g:153:10: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
					{
					match('\\'); 
					if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// SpiderDiagrams.g:154:10: UNICODE_ESC
					{
					mUNICODE_ESC(); 

					}
					break;
				case 3 :
					// SpiderDiagrams.g:155:10: OCTAL_ESC
					{
					mOCTAL_ESC(); 

					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ESC_SEQ"

	// $ANTLR start "OCTAL_ESC"
	public final void mOCTAL_ESC() throws RecognitionException {
		try {
			// SpiderDiagrams.g:160:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
			int alt7=3;
			int LA7_0 = input.LA(1);
			if ( (LA7_0=='\\') ) {
				int LA7_1 = input.LA(2);
				if ( ((LA7_1 >= '0' && LA7_1 <= '3')) ) {
					int LA7_2 = input.LA(3);
					if ( ((LA7_2 >= '0' && LA7_2 <= '7')) ) {
						int LA7_4 = input.LA(4);
						if ( ((LA7_4 >= '0' && LA7_4 <= '7')) ) {
							alt7=1;
						}

						else {
							alt7=2;
						}

					}

					else {
						alt7=3;
					}

				}
				else if ( ((LA7_1 >= '4' && LA7_1 <= '7')) ) {
					int LA7_3 = input.LA(3);
					if ( ((LA7_3 >= '0' && LA7_3 <= '7')) ) {
						alt7=2;
					}

					else {
						alt7=3;
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 7, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// SpiderDiagrams.g:160:10: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// SpiderDiagrams.g:161:10: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 3 :
					// SpiderDiagrams.g:162:10: '\\\\' ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OCTAL_ESC"

	// $ANTLR start "UNICODE_ESC"
	public final void mUNICODE_ESC() throws RecognitionException {
		try {
			// SpiderDiagrams.g:167:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
			// SpiderDiagrams.g:167:10: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
			{
			match('\\'); 
			match('u'); 
			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UNICODE_ESC"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// SpiderDiagrams.g:170:5: ( IdentifierStart ( IdentifierPart )* )
			// SpiderDiagrams.g:170:10: IdentifierStart ( IdentifierPart )*
			{
			mIdentifierStart(); 

			// SpiderDiagrams.g:170:26: ( IdentifierPart )*
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( ((LA8_0 >= '\u0000' && LA8_0 <= '\b')||(LA8_0 >= '\u000E' && LA8_0 <= '\u001B')||LA8_0=='$'||(LA8_0 >= '0' && LA8_0 <= '9')||(LA8_0 >= 'A' && LA8_0 <= 'Z')||LA8_0=='_'||(LA8_0 >= 'a' && LA8_0 <= 'z')||(LA8_0 >= '\u007F' && LA8_0 <= '\u009F')||(LA8_0 >= '\u00A2' && LA8_0 <= '\u00A5')||LA8_0=='\u00AA'||LA8_0=='\u00AD'||LA8_0=='\u00B5'||LA8_0=='\u00BA'||(LA8_0 >= '\u00C0' && LA8_0 <= '\u00D6')||(LA8_0 >= '\u00D8' && LA8_0 <= '\uDFFF')) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// SpiderDiagrams.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\b')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\u001B')||input.LA(1)=='$'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u007F' && input.LA(1) <= '\u009F')||(input.LA(1) >= '\u00A2' && input.LA(1) <= '\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00AD'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\uDFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop8;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "IdentifierStart"
	public final void mIdentifierStart() throws RecognitionException {
		try {
			// SpiderDiagrams.g:176:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00a2' .. '\\u00a5' | '\\u00aa' | '\\u00b5' | '\\u00ba' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\udfff' )
			// SpiderDiagrams.g:
			{
			if ( input.LA(1)=='$'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00A2' && input.LA(1) <= '\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\uDFFF') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IdentifierStart"

	// $ANTLR start "IdentifierPart"
	public final void mIdentifierPart() throws RecognitionException {
		try {
			// SpiderDiagrams.g:190:5: ( '\\u0000' .. '\\u0008' | '\\u000e' .. '\\u001b' | '\\u0024' | '\\u0030' .. '\\u0039' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u007f' .. '\\u009f' | '\\u00a2' .. '\\u00a5' | '\\u00aa' | '\\u00ad' | '\\u00b5' | '\\u00ba' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\udfff' )
			// SpiderDiagrams.g:
			{
			if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\b')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\u001B')||input.LA(1)=='$'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u007F' && input.LA(1) <= '\u009F')||(input.LA(1) >= '\u00A2' && input.LA(1) <= '\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00AD'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\uDFFF') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IdentifierPart"

	@Override
	public void mTokens() throws RecognitionException {
		// SpiderDiagrams.g:1:8: ( CD | CD_BINARY | CD_Car | CD_Car_BINARY | COP | CompleteCOP | DICT | LIST | LUCOP | LUCarCOP | PAIR | PD | SD_BINARY | SD_COMPOUND | SD_FALSE | SD_NULL | SD_PRIMARY | SD_UNARY | SLIST | T__33 | T__34 | T__35 | T__36 | COMMENT | WS | STRING | ID )
		int alt9=27;
		alt9 = dfa9.predict(input);
		switch (alt9) {
			case 1 :
				// SpiderDiagrams.g:1:10: CD
				{
				mCD(); 

				}
				break;
			case 2 :
				// SpiderDiagrams.g:1:13: CD_BINARY
				{
				mCD_BINARY(); 

				}
				break;
			case 3 :
				// SpiderDiagrams.g:1:23: CD_Car
				{
				mCD_Car(); 

				}
				break;
			case 4 :
				// SpiderDiagrams.g:1:30: CD_Car_BINARY
				{
				mCD_Car_BINARY(); 

				}
				break;
			case 5 :
				// SpiderDiagrams.g:1:44: COP
				{
				mCOP(); 

				}
				break;
			case 6 :
				// SpiderDiagrams.g:1:48: CompleteCOP
				{
				mCompleteCOP(); 

				}
				break;
			case 7 :
				// SpiderDiagrams.g:1:60: DICT
				{
				mDICT(); 

				}
				break;
			case 8 :
				// SpiderDiagrams.g:1:65: LIST
				{
				mLIST(); 

				}
				break;
			case 9 :
				// SpiderDiagrams.g:1:70: LUCOP
				{
				mLUCOP(); 

				}
				break;
			case 10 :
				// SpiderDiagrams.g:1:76: LUCarCOP
				{
				mLUCarCOP(); 

				}
				break;
			case 11 :
				// SpiderDiagrams.g:1:85: PAIR
				{
				mPAIR(); 

				}
				break;
			case 12 :
				// SpiderDiagrams.g:1:90: PD
				{
				mPD(); 

				}
				break;
			case 13 :
				// SpiderDiagrams.g:1:93: SD_BINARY
				{
				mSD_BINARY(); 

				}
				break;
			case 14 :
				// SpiderDiagrams.g:1:103: SD_COMPOUND
				{
				mSD_COMPOUND(); 

				}
				break;
			case 15 :
				// SpiderDiagrams.g:1:115: SD_FALSE
				{
				mSD_FALSE(); 

				}
				break;
			case 16 :
				// SpiderDiagrams.g:1:124: SD_NULL
				{
				mSD_NULL(); 

				}
				break;
			case 17 :
				// SpiderDiagrams.g:1:132: SD_PRIMARY
				{
				mSD_PRIMARY(); 

				}
				break;
			case 18 :
				// SpiderDiagrams.g:1:143: SD_UNARY
				{
				mSD_UNARY(); 

				}
				break;
			case 19 :
				// SpiderDiagrams.g:1:152: SLIST
				{
				mSLIST(); 

				}
				break;
			case 20 :
				// SpiderDiagrams.g:1:158: T__33
				{
				mT__33(); 

				}
				break;
			case 21 :
				// SpiderDiagrams.g:1:164: T__34
				{
				mT__34(); 

				}
				break;
			case 22 :
				// SpiderDiagrams.g:1:170: T__35
				{
				mT__35(); 

				}
				break;
			case 23 :
				// SpiderDiagrams.g:1:176: T__36
				{
				mT__36(); 

				}
				break;
			case 24 :
				// SpiderDiagrams.g:1:182: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 25 :
				// SpiderDiagrams.g:1:190: WS
				{
				mWS(); 

				}
				break;
			case 26 :
				// SpiderDiagrams.g:1:193: STRING
				{
				mSTRING(); 

				}
				break;
			case 27 :
				// SpiderDiagrams.g:1:200: ID
				{
				mID(); 

				}
				break;

		}
	}


	protected DFA9 dfa9 = new DFA9(this);
	static final String DFA9_eotS =
		"\1\uffff\2\23\2\uffff\1\23\1\uffff\4\23\11\uffff\1\37\5\23\1\45\4\23\1"+
		"\uffff\1\23\1\53\3\23\1\uffff\5\23\1\uffff\10\23\1\76\3\23\1\102\5\23"+
		"\1\uffff\3\23\1\uffff\3\23\1\117\7\23\1\130\1\uffff\1\131\2\23\1\134\1"+
		"\23\1\136\1\137\1\23\2\uffff\2\23\1\uffff\1\23\2\uffff\1\144\1\23\1\146"+
		"\1\23\1\uffff\1\150\1\uffff\1\151\2\uffff";
	static final String DFA9_eofS =
		"\152\uffff";
	static final String DFA9_minS =
		"\1\11\1\104\1\151\2\uffff\1\125\1\uffff\1\104\1\141\1\165\1\156\11\uffff"+
		"\1\0\1\162\1\120\1\155\1\156\1\103\1\0\1\151\2\154\1\141\1\uffff\1\103"+
		"\1\0\1\160\1\141\1\117\1\uffff\1\155\1\163\1\154\1\162\1\104\1\uffff\1"+
		"\154\1\162\1\120\1\162\1\141\1\145\1\123\1\171\1\0\1\145\1\165\1\171\1"+
		"\0\1\103\1\162\1\123\1\104\1\123\1\uffff\1\164\1\156\1\103\1\uffff\1\117"+
		"\1\171\1\104\1\0\1\104\1\145\1\144\2\104\1\120\1\123\1\0\1\uffff\1\0\1"+
		"\103\1\123\1\0\1\162\2\0\1\104\2\uffff\1\117\1\104\1\uffff\1\103\2\uffff"+
		"\1\0\1\120\1\0\1\104\1\uffff\1\0\1\uffff\1\0\2\uffff";
	static final String DFA9_maxS =
		"\1\udfff\1\157\1\151\2\uffff\1\125\1\uffff\1\162\1\141\1\165\1\156\11"+
		"\uffff\1\udfff\1\162\1\120\1\155\1\156\1\103\1\udfff\1\151\2\154\1\141"+
		"\1\uffff\1\103\1\udfff\1\160\2\141\1\uffff\1\155\1\163\1\154\1\162\1\104"+
		"\1\uffff\1\157\1\162\1\120\1\162\1\141\1\145\1\123\1\171\1\udfff\1\145"+
		"\1\165\1\171\1\udfff\1\103\1\162\1\123\1\104\1\123\1\uffff\1\164\1\156"+
		"\1\123\1\uffff\1\117\1\171\1\104\1\udfff\1\104\1\145\1\144\1\141\1\104"+
		"\1\120\1\123\1\udfff\1\uffff\1\udfff\1\103\1\123\1\udfff\1\162\2\udfff"+
		"\1\104\2\uffff\1\117\1\104\1\uffff\1\103\2\uffff\1\udfff\1\120\1\udfff"+
		"\1\104\1\uffff\1\udfff\1\uffff\1\udfff\2\uffff";
	static final String DFA9_acceptS =
		"\3\uffff\1\7\1\10\1\uffff\1\13\4\uffff\1\23\1\24\1\25\1\26\1\27\1\30\1"+
		"\31\1\32\1\33\13\uffff\1\1\5\uffff\1\14\5\uffff\1\5\22\uffff\1\3\3\uffff"+
		"\1\11\14\uffff\1\20\10\uffff\1\17\1\22\2\uffff\1\2\1\uffff\1\15\1\12\4"+
		"\uffff\1\21\1\uffff\1\16\1\uffff\1\6\1\4";
	static final String DFA9_specialS =
		"\152\uffff}>";
	static final String[] DFA9_transitionS = {
			"\2\21\2\uffff\1\21\22\uffff\1\21\1\uffff\1\22\1\uffff\1\23\3\uffff\1"+
			"\13\1\14\2\uffff\1\15\2\uffff\1\20\15\uffff\1\6\3\uffff\1\23\1\2\1\1"+
			"\2\23\1\10\5\23\1\5\1\23\1\11\1\23\1\7\4\23\1\12\5\23\1\4\1\uffff\1\16"+
			"\1\uffff\1\23\1\uffff\32\23\1\3\1\uffff\1\17\44\uffff\4\23\4\uffff\1"+
			"\23\12\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\24\12\uffff\1\26\21\uffff\1\25\15\uffff\1\27",
			"\1\30",
			"",
			"",
			"\1\31",
			"",
			"\1\32\55\uffff\1\33",
			"\1\34",
			"\1\35",
			"\1\36",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\40",
			"\1\41",
			"\1\42",
			"\1\43",
			"\1\44",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\46",
			"\1\47",
			"\1\50",
			"\1\51",
			"",
			"\1\52",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\54",
			"\1\55",
			"\1\56\21\uffff\1\57",
			"",
			"\1\60",
			"\1\61",
			"\1\62",
			"\1\63",
			"\1\64",
			"",
			"\1\65\2\uffff\1\66",
			"\1\67",
			"\1\70",
			"\1\71",
			"\1\72",
			"\1\73",
			"\1\74",
			"\1\75",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\77",
			"\1\100",
			"\1\101",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\103",
			"\1\104",
			"\1\105",
			"\1\106",
			"\1\107",
			"",
			"\1\110",
			"\1\111",
			"\1\112\17\uffff\1\113",
			"",
			"\1\114",
			"\1\115",
			"\1\116",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\120",
			"\1\121",
			"\1\122",
			"\1\123\34\uffff\1\124",
			"\1\125",
			"\1\126",
			"\1\127",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\132",
			"\1\133",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\135",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\140",
			"",
			"",
			"\1\141",
			"\1\142",
			"",
			"\1\143",
			"",
			"",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\145",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"\1\147",
			"",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"",
			"\11\23\5\uffff\16\23\10\uffff\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff"+
			"\1\23\1\uffff\32\23\4\uffff\41\23\2\uffff\4\23\4\uffff\1\23\2\uffff\1"+
			"\23\7\uffff\1\23\4\uffff\1\23\5\uffff\27\23\1\uffff\udf28\23",
			"",
			""
	};

	static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
	static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
	static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
	static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
	static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
	static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
	static final short[][] DFA9_transition;

	static {
		int numStates = DFA9_transitionS.length;
		DFA9_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
		}
	}

	protected class DFA9 extends DFA {

		public DFA9(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 9;
			this.eot = DFA9_eot;
			this.eof = DFA9_eof;
			this.min = DFA9_min;
			this.max = DFA9_max;
			this.accept = DFA9_accept;
			this.special = DFA9_special;
			this.transition = DFA9_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( CD | CD_BINARY | CD_Car | CD_Car_BINARY | COP | CompleteCOP | DICT | LIST | LUCOP | LUCarCOP | PAIR | PD | SD_BINARY | SD_COMPOUND | SD_FALSE | SD_NULL | SD_PRIMARY | SD_UNARY | SLIST | T__33 | T__34 | T__35 | T__36 | COMMENT | WS | STRING | ID );";
		}
	}

}

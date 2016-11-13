package utils.status;

public class Status {
	public static class USER_STATE {
		public static final String ACTIVE = "A";
		public static final String NEW = "N";
		public static final String LOCKED = "L";

		public static final String ACTIVE_TEXT = "Aktywny";
		public static final String NEW_TEXT = "Nowy";
		public static final String LOCKED_TEXT = "Zablokowany";

		public static String getText(String userState) {
			switch (userState) {
				case ACTIVE :
					return ACTIVE_TEXT;
				case NEW :
					return NEW_TEXT;
				case LOCKED :
					return LOCKED_TEXT;
				default :
					return userState;
			}

		}

	}
	public static class ZGLOSZENIE_STATE {
		public static final String REALIZOWANE = "R";
		public static final String NOWE = "N";
		public static final String ZAKONCZONE = "Z";

		public static final String REALIZOWANE_TEXT = "Realizowane";
		public static final String NOWE_TEXT = "Nowe";
		public static final String ZAKONCZONE_TEXT = "Zakończone";

		public static String getText(String zgloszenieState) {
			switch (zgloszenieState) {
				case REALIZOWANE :
					return REALIZOWANE_TEXT;
				case NOWE :
					return NOWE_TEXT;
				case ZAKONCZONE :
					return ZAKONCZONE_TEXT;
				default :
					return zgloszenieState;
			}

		}

	}
	public static class ZAMOWIENIE_STATE {
		public static final String REALIZOWANE = "R";
		public static final String NOWE = "N";
		public static final String W_DOSTAWIE = "D";
		public static final String ANULOWANE = "A";
		public static final String ZREALIZOWANE = "Z";

		public static final String REALIZOWANE_TEXT = "Realizowane";
		public static final String NOWE_TEXT = "Nowe";
		public static final String W_DOSTWIE_TEXT = "W dostawie";
		public static final String ANULOWANE_TEXT = "Anulowane";
		public static final String ZREALIZOWANE_TEXT = "Zrealizowane";

		public static String getText(String zamowienieState) {
			switch (zamowienieState) {
				case REALIZOWANE :
					return REALIZOWANE_TEXT;
				case NOWE :
					return NOWE_TEXT;
				case W_DOSTAWIE :
					return W_DOSTWIE_TEXT;
				case ANULOWANE_TEXT :
					return ANULOWANE;
				case ZREALIZOWANE :
					return ZREALIZOWANE_TEXT;
				default :
					return zamowienieState;
			}

		}
	}

}

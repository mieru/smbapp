package utils.status;

public class Status {
	public static class FAKTURA_STATE {
		public static final String SPRZEDARZ = "S";
		public static final String ZAKUP = "K";

		public static final String SPRZEDARZ_TEXT = "Sprzedaz";
		public static final String ZAKUP_TEXT = "Zakup";

		public static String getText(String fakState) {
			switch (fakState) {
				case SPRZEDARZ :
					return SPRZEDARZ_TEXT;
				case ZAKUP :
					return ZAKUP_TEXT;
				default :
					return fakState;
			}

		}

	}
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
	public static class USER_ROLE {
		public static final String CUSTOMER = "C";
		public static final String ADMIN = "A";
		public static final String EMPLOYEE = "E";
		public static final String EMPLOYEE_ADMIN = "E,A";
		public static final String EMPLOYEE_MAGAZYN = "E,M";
		public static final String EMPLOYEE_ADMIN_MAGAZYN = "E,A,M";

		public static final String CUSTOMER_TEXT = "Klient";
		public static final String ADMIN_TEXT = "Admin";
		public static final String EMPLOYEE_TEXT = "Pracownik";
		public static final String EMPLOYEE_ADMIN_TEXT = "Pracownik, Administrator";
		public static final String EMPLOYEE_MAGAZYN_TEXT = "Pracownik, Magazynier";
		public static final String EMPLOYEE_ADMIN_MAGAZYN_TEXT = "Pracownik, Administrator, Magazynier";

		public static String getText(String userState) {
			switch (userState) {
				case CUSTOMER :
					return CUSTOMER_TEXT;
				case ADMIN :
					return ADMIN_TEXT;
				case EMPLOYEE :
					return EMPLOYEE_TEXT;
				case EMPLOYEE_ADMIN :
					return EMPLOYEE_ADMIN_TEXT;
				case EMPLOYEE_ADMIN_MAGAZYN :
					return EMPLOYEE_ADMIN_MAGAZYN_TEXT;
				case EMPLOYEE_MAGAZYN :
					return EMPLOYEE_MAGAZYN_TEXT;
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
				case ANULOWANE :
					return ANULOWANE_TEXT;
				case ZREALIZOWANE :
					return ZREALIZOWANE_TEXT;
				default :
					return zamowienieState;
			}

		}
	}

}

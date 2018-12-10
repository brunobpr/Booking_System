
public class Validation {
	private String email;
	// private String password;

	public Validation() {

	}

	Validation(String email, String password) {
		// this.password = password;
		this.email = email;
	}

	public boolean checkPassword(String password) {
		int hasUppercase = 0;
		int hasLowercase = 0;
		int hasNumber = 0;
		int hasSpecial = 0;
		boolean isValid = false;
		boolean size = false;
		String[] passArray = new String[password.length()];
		if (password.length() >= 8 && password.length() <= 12) {
			size = true;
		}
		for (int i = 0; i <= password.length() - 1; i++) {
			passArray[i] = password.substring(i, i + 1);
			if (passArray[i].matches("[A-Z]+")) {
				hasUppercase++;
			}
			if (passArray[i].matches("[a-z]+")) {
				hasLowercase++;
			}
			if (passArray[i].matches("\\p{Punct}+")) {
				hasSpecial++;
			}
			if (passArray[i].matches("[0-9]+")) {
				hasNumber++;
			}
		}
		if(hasSpecial > 0 && hasNumber > 0 && hasLowercase > 0 && hasUppercase > 0) {
			isValid = true;
		}
		return size && isValid;
	}
	
	public boolean checkEmail(String email){
		int hasDot = 0;
		int hasAt = 0;
		boolean size = false;
		boolean isValid = false;
		String[] emailArray = new String[email.length()];
		if (email.length() >= 4) {
			size = true;
		}
		for (int i = 0; i <= email.length() - 1; i++) {
			emailArray[i] = email.substring(i, i + 1);
			if(emailArray[i].equals("@")) {
				hasAt++;
			}
			if(emailArray[i].equals(".")) {
				hasDot++;
			}
		}
		if(hasDot > 0 && hasAt > 0) {
			isValid = true;
		}
		
		return size && isValid;
	}
	
	public boolean checkLetter(String text) {
		boolean isText = false;
		if(text.matches("[a-z]+") || text.matches("[A-Z]+") || text.matches("[a-zA-Z]+")) {
			isText = true;
		}
		return isText;
	}
}

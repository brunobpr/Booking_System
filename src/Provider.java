public class Provider{
		private String first_name;
		private String last_name;
		private String phone;
		private String city;
		private String email;
		private String area;
		private String address;
		private String status;
		
		Provider(){
			
		}
		
		Provider(String first_name, String last_name, String phone, String city, String email, String area, String address, String status ){
			this.first_name = first_name;
			this.last_name = last_name;
			this.phone = phone;
			this.city = city;
			this.area = area;
			this.address = address;
			this.status = status;
			this.email = email;
		}

		public String getFirst_name() {
			return first_name;
		}

		public String getLast_name() {
			return last_name;
		}

		public String getPhone() {
			return phone;
		}

		public String getCity() {
			return city;
		}

		public String getEmail() {
			return email;
		}

		public String getArea() {
			return area;
		}

		public String getAddress() {
			return address;
		}

		public String getStatus() {
			return status;
		}

		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}

		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
	}

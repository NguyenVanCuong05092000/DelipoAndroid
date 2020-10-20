package jp.adapter.delipo.entity.api;

public class LoginResponseEntity {
    public String id;
    public String name;
    public String email;
    public String status;
    public String gender;
    public String birthday;
    public String job;
    public String family_number;
    public String family_member;
    public String zip_code;
    public String family_income;
    public String last_login_for_api;
    public String create_at;
    public String update_at;
    public String token;

    public LoginResponseEntity(String id, String name, String email, String status, String gender, String birthday, String job, String family_number, String family_member,
                               String zip_code, String family_income, String last_login_for_api, String create_at, String update_at, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.gender = gender;
        this.birthday = birthday;
        this.job = job;
        this.family_number = family_number;
        this.family_member = family_member;
        this.zip_code = zip_code;
        this.family_income = family_income;
        this.last_login_for_api = last_login_for_api;
        this.create_at = create_at;
        this.update_at = update_at;
        this.token = token;
    }
}

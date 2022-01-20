package view.tm;

public class CoachTM {
    private String coachId;
    private String name;
    private String contact;
    private String gender;
    private String address;
    private int age;
    private String userName;
    private String password;

    public CoachTM() {

    }

    public CoachTM(String coachId, String name, String contact, String gender, String address, int age, String userName, String password) {
        this.setCoachId(coachId);
        this.setName(name);
        this.setContact(contact);
        this.setGender(gender);
        this.setAddress(address);
        this.setAge(age);
        this.setUserName(userName);
        this.setPassword(password);
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CoachTM{" +
                "coachId='" + coachId + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

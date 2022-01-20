package model;

public class Member {
    private String id;
    private String name;
    private String contact;
    private String gender;
    private String address;
    private String birthday;
    private String occupation;

    public Member() {

    }

    public Member(String id, String name, String contact, String gender, String address, String birthday, String occupation) {
        this.setId(id);
        this.setName(name);
        this.setContact(contact);
        this.setGender(gender);
        this.setAddress(address);
        this.setBirthday(birthday);
        this.setOccupation(occupation);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", occupation='" + occupation + '\'' +
                '}';
    }
}

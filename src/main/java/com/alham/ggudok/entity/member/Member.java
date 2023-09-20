package com.alham.ggudok.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"memberId","memberName","age","loginId"})
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    private String memberName;
    private int age;
    private String loginId;
    private String password;
    @Enumerated(STRING)
    private Gender gender;
    private String phoneNumber;


    @OneToMany(mappedBy = "member", cascade = ALL)
    private List<MemberRelTag> memberRelTags = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = ALL)
    private List<MemberHaveSubs> memberHaveSubsList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = ALL)
    private List<MemberFavorSubs> memberFavorSubsList = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = ALL)
    private List<Review> reviews = new ArrayList<>();

    public Member(String memberName, int age) {
        this.memberName = memberName;
        this.age = age;
    }

    public Member(String memberName, int age, String loginId, String password, Gender gender, String phoneNumber) {
        this.memberName = memberName;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public void updateMember(String password,String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}

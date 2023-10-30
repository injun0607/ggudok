package com.alham.ggudok.entity.member;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = {"memberId"})
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


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.GUEST;
    private String phoneNumber;

    private String profileImage;


    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<MemberRelTag> memberRelTags = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<MemberHaveSubs> memberHaveSubsList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<MemberFavorSubs> memberFavorSubsList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
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

    public Member(String loginId,String memberName) {
        this.memberName = memberName;
        this.loginId = loginId;
    }

    public void updateMember(String newPassword,String phoneNumber,Gender gender,int age,String profileImage ) {
        this.password = newPassword;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
        this.profileImage = profileImage;
    }

    public void updateMember(String phoneNumber,Gender gender,int age,String profileImage ) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
    }

    public void updateMember(String memberName ) {
        this.memberName = memberName;
    }

    public static Member noMember() {
        return new Member("no-member",0);
    }

    public int updateFavorSubs(List<MemberFavorSubs> memberFavorSubsList) {
        this.memberFavorSubsList = memberFavorSubsList;
        return this.memberFavorSubsList.size();
    }

    public int updateReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this.memberFavorSubsList.size();
    }


    public int updateHaveSubs(List<MemberHaveSubs> memberHaveSubsList) {
        this.memberHaveSubsList = memberHaveSubsList;
        return this.memberHaveSubsList.size();
    }

    public int updateMemberRelTag(List<MemberRelTag> changeMemberRelTags) {
        this.memberRelTags = changeMemberRelTags;
        return memberRelTags.size();
    }

    public void updateImage(String profileImage) {
        this.profileImage = profileImage;
    }
}

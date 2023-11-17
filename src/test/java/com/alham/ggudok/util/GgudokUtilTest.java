package com.alham.ggudok.util;

import com.alham.ggudok.entity.member.Member;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GgudokUtilTest {

    @Test
    public void test()throws Exception{
        //given
        String mail = "";
        //when
        String s = GgudokUtil.certEmail(mail);

        //then

    }
    @Test
    public void teamTest()throws Exception{
        //given
        User user1 = new User("injun");
        User user2 = new User("seohee");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Team team1 = new Team(users);

        List<User> findUser = team1.getUsers();
        User findUser1 = findUser.get(0);
        findUser1.setName("injun123");

        assertEquals(team1.getUsers().get(0).getName(),"injun123");
        assertEquals(user1.getName(),"injun123");

    }


    public static class Team {
        private List<Member> members;
        private List<User> users;


        Team(List<User> users) {
            this.users = users;
        }

        public List<Member> getMembers() {
            return members;
        }

        public List<User> getUsers() {
            return users;
        }
    }

    public static class User{
        private String name;

        User(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
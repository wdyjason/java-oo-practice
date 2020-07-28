package com.twu;

import com.twu.model.HotTag;
import com.twu.utils.ConsoleInteraction;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String userName = "";
    private static int currentUserTickets = 10;
    private static String adminName = "admin";
    private static String adminPsw = "admin123";
    private static List<HotTag> hotTagList = new ArrayList<>();
    private static ConsoleInteraction ci = new ConsoleInteraction();

    public static void main(String[] args) {
        int ret;
        while (true) {
            ret = ci.initOutPut();
            if (ret < 1 || ret > 3) continue;
            if (ret == 3) System.exit(0);
            if (ret == 2 && ci.adminLogin(adminName, adminPsw)) adminFlow();
            if (ret == 1) {
                userName = ci.newUserLogin();
                currentUserTickets = 10;
                if (!userName.isEmpty()) userFlow();
            }
        }
    }

    public static void adminFlow() {
        int ret;
        while (true) {
           ret = ci.adminMain();
           if (ret < 1 || ret > 4) continue;
           if (ret == 4) return;
           if (ret == 3) ci.addSuperHot(hotTagList);
           if (ret == 2) ci.addHotTags(hotTagList);
           if (ret == 1) hotTagList = ci.showHotTagList(hotTagList);
        }
    }

    public static void userFlow() {
        int ret;
        while (true) {
            ret = ci.userMain(userName);
            if (ret < 1 || ret > 5) continue;
            if (ret == 5) return;
            if (ret == 4) ci.addHotTags(hotTagList);
            if (ret == 3) ci.buyHotTag(hotTagList);
            if (ret == 2) {
                int voteSuccessfulTickets = ci.vote(hotTagList, currentUserTickets);
                if (voteSuccessfulTickets > 0) currentUserTickets -= voteSuccessfulTickets;
            }
            if (ret == 1) hotTagList = ci.showHotTagList(hotTagList);
        }
    }
}

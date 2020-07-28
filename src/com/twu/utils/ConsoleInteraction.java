package com.twu.utils;

import com.twu.model.HotTag;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleInteraction {

    private   Scanner scanner;

    public ConsoleInteraction() {
        this.scanner = new Scanner(System.in);
    }

    public int initOutPut() {
        System.out.print("欢迎来到热搜系统,请选择登录身份\n" +
                "1. 用户登录\n" +
                "2. 管理员登陆\n" +
                "3. 退出系统\n");
        return scanner.nextInt();
    }

    public String newUserLogin() {
        System.out.println("请输入用户昵称");
        String userName = scanner.next();
        if (userName.isEmpty()) System.out.println("用户昵称不能为空");
        return userName;
    }

    public int userMain(String userName) {
        System.out.print(userName + ", 请选择操作\n" +
                "1. 查看热搜排行榜\n" +
                "2. 给热搜事件投票\n" +
                "3. 购买热搜\n" +
                "4. 添加热搜\n" +
                "5. 退出\n");
        return scanner.nextInt();
    }

    public Boolean adminLogin(String name, String psw) {
        System.out.println("请输入管理员账号名");
        if (name.equals(scanner.next())) {
            System.out.println("请输入管理员密码");
            if (psw.equals(scanner.next())) {
                System.out.println("登录成功");
                return true;
            }
            System.out.println("登录失败, 密码错误");
            return false;
        }
        System.out.println("登录失败, 用户名错误");
        return false;
    }

    public int adminMain() {
        System.out.print("管理员, 请选择操作\n" +
                "1. 查看热搜排行榜\n" +
                "2. 添加热搜\n" +
                "3. 添加超级热搜\n" +
                "4. 退出\n");
        return scanner.nextInt();
    }

    public List<HotTag> showHotTagList(List<HotTag> hotTags) {
        if (hotTags.isEmpty()) {
            System.out.println("暂无数据");
            return hotTags;
        }
        hotTags = sortTags(hotTags);
        for (int i = 0; i < hotTags.size(); i++) {
            System.out.println(i + 1 + ". " + hotTags.get(i).getName() + " 热度 " + hotTags.get(i).getTickets());
        }
        return hotTags;
    }

    public int vote(List<HotTag> hotTags, int ticketsLimits) {
        System.out.println("请输入投票热搜名");
        String voteName = scanner.next();
        for (HotTag ht : hotTags) {
            if (ht.getName().equalsIgnoreCase(voteName)) {
                System.out.println("请输入投票数, 当前剩余 " + ticketsLimits + "票");
                int tickets = scanner.nextInt();
                if (tickets > ticketsLimits || tickets < 1) {
                    System.out.println("投票数超过限制!");
                    return -1;
                }
                int addTickets = tickets;
                if (ht.getSuperHot()) addTickets = 2 * tickets;
                ht.addTickets(addTickets);
                System.out.println("投" + tickets + "票给 " + voteName + "成功");
                return tickets;
            }
        }
        System.out.println("此热搜不存在!");
        return -1;
    }

    public Boolean addHotTags(List<HotTag> hotTags) {
        System.out.println("请输入添加热搜名");
        String addName = scanner.next();
        if (addName.isEmpty()) {
            System.out.println("不允许空字串");
            return false;
        }
        for (HotTag ht : hotTags) {
            if (ht.getName().equalsIgnoreCase(addName)) {
                System.out.println("热搜已存在");
                return false;
            }
        }
        hotTags.add(new HotTag(addName));
        System.out.println("添加热搜" + addName + "成功");
        return true;
    }

    public Boolean buyHotTag(List<HotTag> hotTags) {
        System.out.println("请输入欲购买的热搜名");
        String addName = scanner.next();
        if (addName.isEmpty()) {
            System.out.println("不允许空字串");
            return false;
        }
        int index = -1;
        for (int i = 0; i < hotTags.size(); i ++) {
            if (hotTags.get(i).getName().equalsIgnoreCase(addName)) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            System.out.println("热搜不存在");
            return false;
        }
        System.out.println("请选择欲购买热搜的位置");
        int position = scanner.nextInt();
        if (position < 1 || position >hotTags.size()) {
            System.out.println("排名超出限制");
            return false;
        }
        System.out.println("请选择欲购买的金额" + " 当前价格 " + hotTags.get(index).getPayMoney() + "元");
        int money = scanner.nextInt();
        if (money <= hotTags.get(index).getPayMoney()) {
            System.out.println("金额不足!");
            return false;
        }
        hotTags.get(index).setPayMoney(money);
        hotTags.get(index).setPayPosition(position);
        System.out.println("花" + money + "元购买" + addName + "第" + position + "名成功");
        return true;
    }

    public Boolean addSuperHot(List<HotTag> hotTags) {
        System.out.println("请输入欲添加超级热搜的热搜名");
        String addName = scanner.next();
        if (addName.isEmpty()) {
            System.out.println("不允许空字串");
            return false;
        }
        int index = -1;
        for (int i = 0; i < hotTags.size(); i ++) {
            if (hotTags.get(i).getName().equalsIgnoreCase(addName)) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            System.out.println("热搜不存在");
            return false;
        }
        hotTags.get(index).setSuperHot(true);
        System.out.println("为添加" + addName +"超级热搜成功");
        return true;
    }

    public List<HotTag> sortTags(List<HotTag> hotTags) {
        List<HotTag> boughtTags = hotTags.stream().filter( f -> f.getPayMoney() > 0 && f.getPayPosition() > 0)
                .sorted(Comparator.comparing(HotTag::getPayPosition)).collect(Collectors.toList());
        hotTags.removeAll(boughtTags);
        hotTags = hotTags.stream().sorted(Comparator.comparing(HotTag::getTickets).reversed()).collect(Collectors.toList());

        for (HotTag ht: boughtTags) {
            hotTags.add(ht.getPayPosition() -1 , ht);
        }
        return hotTags;
    }
}

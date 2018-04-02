package com.kaishengit.proxy;

public class Mp3LogProxy implements Player {

    //目标对象
    private Player target;

    public Mp3LogProxy(Player target) {
        this.target = target;
    }

    public void play(String musicName) {
        System.out.println("方法:play开始调用");
        target.play(musicName);
        System.out.println("方法:play调用结束");
    }

    public int stop() {
        target.stop();
        return 11;
    }
}

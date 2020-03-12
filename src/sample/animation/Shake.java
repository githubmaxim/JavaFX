package sample.animation;

import javafx.animation.TranslateTransition; //устаревший класс для элементарных действий
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;

    public Shake(Node node) {
        //говорим, что объект будет трястись, время 70 млсек
        tt = new TranslateTransition(Duration.millis(150), node);
        tt.setFromX(0f); //отступ по х ноль
        tt.setByX(10f); //размер смещения при тряске по х
        tt.setCycleCount(3); //сколько раз протрясется
        tt.setAutoReverse(true); //чтобы объект при тряске возвращался обратно
        //Так устанавливается только одно свойство (тряска по х), если хотим установить
        // тряску по у, то нужно прописывать еше один tt1. Иначе setAutoReverse(true) не
        //будет возвращать трясущийся элемент на исходную и он будет сдвигаться вниз
    }

    public void playAnim() {
        tt.playFromStart(); //запускает на выполнение анимацию из tt
    }
}

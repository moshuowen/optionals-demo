package cn.msw.lambda;

/**
 *
 * 函数式接口只能有一个抽象方法
 *
 * @author moshu
 */
@FunctionalInterface
public interface Printable {

    String print(String prefix, String suffix);

}

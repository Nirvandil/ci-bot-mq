package cf.nirvandil.cibotmq.model;

public interface Handlable {
    boolean isHandled();

    void setHandled(boolean handled);
}

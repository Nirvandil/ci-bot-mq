package cf.nirvandil.cibotmq.service;

public interface IMessageDispatcher<T> {
    void dispatch(T message);
}

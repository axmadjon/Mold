package uz.mold.variable;

public interface Variable {

    void readyToChange();

    boolean modified();

    ErrorResult getError();

}

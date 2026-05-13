public class BranchNode<T> extends BaseNode<T> {
    BranchNode<T> leftBranch, rightBranch;

    public BranchNode(T value) {
        super(value);
        this.leftBranch = null;
        this.rightBranch = null;
    }
}

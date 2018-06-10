package onedirection.cn.one;

public class Translate {
    private String index;

    private String src;

    private String dst;

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Translate(String index,String src,String dst) {
        this.index = index;
        this.src = src;
        this.dst = dst;
    }

    @Override
    public String toString() {
        return "Translate{" +
                "index='" + index + '\'' +
                ", src='" + src + '\'' +
                ", dst='" + dst + '\'' +
                '}';
    }
}

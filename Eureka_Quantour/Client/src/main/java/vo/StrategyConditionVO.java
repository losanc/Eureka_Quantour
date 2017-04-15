package vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huihantao on 2017/4/6.
 */
public class StrategyConditionVO implements Serializable{
    private static final long serialVersionUID = 3441219408241335848L;

    private String name;
    private List<Object> extra;//动量策略、均值策略
    private int nums;

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getName() {
        return name;
    }

    public List<Object> getExtra() {
        return extra;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtra(List<Object> extra) {
        this.extra = extra;
    }

}

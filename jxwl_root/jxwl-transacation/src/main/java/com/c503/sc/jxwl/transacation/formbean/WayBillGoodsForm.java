package com.c503.sc.jxwl.transacation.formbean;

import com.c503.sc.base.entity.BaseEntity;

/**
 * 
 * 〈一句话功能简述〉运单信息实体类
 * 〈功能详细描述〉
 * @author    wl
 * @version   [版本号, 2016-08-30]
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
public class WayBillGoodsForm extends BaseEntity{

    /** 序列号 */
    private static final long serialVersionUID = 2244395988127433615L;

    /**托运单号*/
    private String checkno;

    /**货物名称*/
    private String goodsname;

    /**包装*/
    private String pack;

    /**数量*/
    private String quantity;

    /**重量*/
    private String weight;

    /**体积*/
    private String volume;

    /**规格*/
    private String format;

    /**主表ID*/
    private String waybillId;

    /**批次*/
    private String batch;
    
    /**模板ID*/
    private String waybilltempId;

    /**
     *〈一句话功能简述〉托运单号
     * 〈功能详细描述〉
     * @return checkno
     * @see  [类、类#方法、类#成员]
     */
    public String getCheckno() {
        return checkno;
    }

    /**
     *〈一句话功能简述〉托运单号
     * 〈功能详细描述〉
     * @param checkno 托运单号
     * @see  [类、类#方法、类#成员]
     */
    public void setCheckno(String checkno) {
        this.checkno = checkno == null ? null : checkno.trim();
    }
    
    /**
     *〈一句话功能简述〉货物名称
     * 〈功能详细描述〉
     * @return goodsname
     * @see  [类、类#方法、类#成员]
     */
    public String getGoodsname() {
        return goodsname;
    }
    
    /**
     *〈一句话功能简述〉货物名称
     * 〈功能详细描述〉
     * @param goodsname 货物名称
     * @see  [类、类#方法、类#成员]
     */
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }

    /**
     *〈一句话功能简述〉包装
     * 〈功能详细描述〉
     * @return pack
     * @see  [类、类#方法、类#成员]
     */
    public String getPack() {
        return pack;
    }

    /**
     *〈一句话功能简述〉包装
     * 〈功能详细描述〉
     * @param pack 包装
     * @see  [类、类#方法、类#成员]
     */
    public void setPack(String pack) {
        this.pack = pack == null ? null : pack.trim();
    }

    /**
     *〈一句话功能简述〉数量
     * 〈功能详细描述〉
     * @return quantity
     * @see  [类、类#方法、类#成员]
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     *〈一句话功能简述〉数量
     * 〈功能详细描述〉
     * @param quantity 数量
     * @see  [类、类#方法、类#成员]
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity == null ? null : quantity.trim();
    }

    /**
     *〈一句话功能简述〉重量 
     * 〈功能详细描述〉
     * @return weight
     * @see  [类、类#方法、类#成员]
     */
    public String getWeight() {
        return weight;
    }

    /**
     *〈一句话功能简述〉重量
     * 〈功能详细描述〉
     * @param weight 重量
     * @see  [类、类#方法、类#成员]
     */
    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    /**
     *〈一句话功能简述〉体积
     * 〈功能详细描述〉
     * @return volume
     * @see  [类、类#方法、类#成员]
     */
    public String getVolume() {
        return volume;
    }

    /**
     *〈一句话功能简述〉体积
     * 〈功能详细描述〉
     * @param volume  体积
     * @see  [类、类#方法、类#成员]
     */
    public void setVolume(String volume) {
        this.volume = volume == null ? null : volume.trim();
    }

    /**
     *〈一句话功能简述〉规格
     * 〈功能详细描述〉
     * @return format
     * @see  [类、类#方法、类#成员]
     */
    public String getFormat() {
        return format;
    }

    /**
     *〈一句话功能简述〉规格
     * 〈功能详细描述〉
     * @param format 规格
     * @see  [类、类#方法、类#成员]
     */
    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    /**
     *〈一句话功能简述〉主表ID
     * 〈功能详细描述〉
     * @return waybillId
     * @see  [类、类#方法、类#成员]
     */
    public String getWaybillId() {
        return waybillId;
    }
    
    /**
     *〈一句话功能简述〉主表ID
     * 〈功能详细描述〉
     * @param waybillId 主表ID
     * @see  [类、类#方法、类#成员]
     */
    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId == null ? null : waybillId.trim();
    }

    /**
     *〈一句话功能简述〉批次
     * 〈功能详细描述〉
     * @return batch
     * @see  [类、类#方法、类#成员]
     */
    public String getBatch() {
        return batch;
    }

    /**
     *〈一句话功能简述〉批次
     * 〈功能详细描述〉
     * @param batch 批次
     * @see  [类、类#方法、类#成员]
     */
    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    /**
     *〈一句话功能简述〉模板ID
     * 〈功能详细描述〉
     * @return waybilltempId
     * @see  [类、类#方法、类#成员]
     */
    public String getWaybilltempId() {
        return waybilltempId;
    }

    /**
     *〈一句话功能简述〉模板ID
     * 〈功能详细描述〉
     * @param batch 模板ID
     * @see  [类、类#方法、类#成员]
     */
    public void setWaybilltempId(String batch) {
        this.waybilltempId = batch == null ? null : batch.trim();
    }


    @Override
	protected Object getBaseEntity() {
		return this;
	}

}

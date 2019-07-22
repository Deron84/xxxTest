package com.sdses.bo.pos;

import java.util.List;

import com.sdses.po.TblVegeMappingInfo;
import com.sdses.po.TblVegeMappingInfoPK;

/**
 *  菜品编码BO
 * //TODO T30108BO
 * //TODO T30108BOTarget/菜品编码表的增删改查操作
 * 
 * @author  hanyongqing
 * @version
 */
public interface T30501BO {
   /**
    * 
    * //TODO 获取终端菜品映射表的相关信息（主键）
    *
    * @param brhId
    * @return
    * @author hanyongqing
    */
    public TblVegeMappingInfo get(TblVegeMappingInfoPK tblVegeMappingInfoPK);
//    /**
//     * 
//     * //TODO 获取菜单编码表的相关信息，根据菜名（非主键）
//     *
//     * @param name
//     * @return
//     * @author hanyongqing
//     */
//    public List<Object[]> findListByName(String name);
    /**
     * 
     * //TODO 终端菜品映射表增加信息
     *
     * @param tblBrhInfo
     * @return
     * @author hanyongqing
     */
    public String add(TblVegeMappingInfo tblVegeMappingInfo);
    /**
     * 
     * //TODO 删除相关的记录信息
     *
     * @param vegCode
     * @return
     * @author hanyongqing
     */
    public String delete(TblVegeMappingInfo tblVegeMappingInfo);
    /**
     * 
     * //TODO 更新修改的记录
     *
     * @param tblBrhInfoList
     * @return
     * @author hanyongqing
     */
    public String update( List<TblVegeMappingInfo> vegeInfoList);
    
}

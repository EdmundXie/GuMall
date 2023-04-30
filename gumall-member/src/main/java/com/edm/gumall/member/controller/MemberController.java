package com.edm.gumall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.edm.gumall.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edm.gumall.member.entity.MemberEntity;
import com.edm.gumall.member.service.MemberService;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.R;



/**
 * 会员
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 22:49:31
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;


    @RequestMapping("/coupons")
    public R getCoupons(){
        MemberEntity member = new MemberEntity();
        member.setNickname("Edmund");
        R r = couponFeignService.memberCoupons();
        return R.ok().put("member",member).put("coupons",r.get("coupons"));
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

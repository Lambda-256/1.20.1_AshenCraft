package com.MissFrom.AshenMod.main.skill.passive;

import net.minecraft.server.level.ServerPlayer;

public interface IPassiveSkill {
    /** パッシブスキルを適用する */
    void apply(ServerPlayer player);

    /** パッシブスキルを除去する */
    void remove(ServerPlayer player);

    /** 適用条件を満たしているかチェック */
    boolean canApply(ServerPlayer player);

    /** スキル名を取得 */
    String getSkillName();
}

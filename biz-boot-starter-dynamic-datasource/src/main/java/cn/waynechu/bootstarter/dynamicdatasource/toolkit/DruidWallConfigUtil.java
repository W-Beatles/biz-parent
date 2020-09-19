/**
 * Copyright © 2018 organization waynechu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.waynechu.bootstarter.dynamicdatasource.toolkit;

import cn.waynechu.bootstarter.dynamicdatasource.config.DruidWallConfig;
import com.alibaba.druid.wall.WallConfig;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * 防火墙配置工具类 解决打war包外部部署的异常
 *
 * @author zhuwei
 * @since 2020-03-19 23:52
 */
@UtilityClass
public class DruidWallConfigUtil {

    /**
     * 根据当前的配置和全局的配置生成druid防火墙配置
     *
     * @param config       当前配置
     * @param globalConfig 全局配置
     * @return 防火墙配置
     */
    public static WallConfig toWallConfig(DruidWallConfig config, DruidWallConfig globalConfig) {
        WallConfig wallConfig = new WallConfig();

        String tempDir = StringUtils.isEmpty(config.getDir()) ? globalConfig.getDir() : config.getDir();
        if (!StringUtils.isEmpty(tempDir)) {
            wallConfig.loadConfig(tempDir);
        }
        String tempTenantTablePattern = StringUtils.isEmpty(config.getTenantTablePattern()) ? globalConfig.getTenantTablePattern() : config.getTenantTablePattern();
        if (!StringUtils.isEmpty(tempTenantTablePattern)) {
            wallConfig.setTenantTablePattern(tempTenantTablePattern);
        }
        String tempTenantColumn = StringUtils.isEmpty(config.getTenantColumn()) ? globalConfig.getTenantColumn() : config.getTenantColumn();
        if (!StringUtils.isEmpty(tempTenantColumn)) {
            wallConfig.setTenantTablePattern(tempTenantColumn);
        }
        Boolean tempNoneBaseStatementAllow = config.getNoneBaseStatementAllow() == null ? globalConfig.getNoneBaseStatementAllow() : config.getNoneBaseStatementAllow();
        if (tempNoneBaseStatementAllow != null && tempNoneBaseStatementAllow) {
            wallConfig.setNoneBaseStatementAllow(true);
        }
        Integer tempInsertValuesCheckSize = config.getInsertValuesCheckSize() == null ? globalConfig.getInsertValuesCheckSize() : config.getInsertValuesCheckSize();
        if (tempInsertValuesCheckSize != null) {
            wallConfig.setInsertValuesCheckSize(tempInsertValuesCheckSize);
        }
        Integer tempSelectLimit = config.getSelectLimit() == null ? globalConfig.getSelectLimit() : config.getSelectLimit();
        if (tempSelectLimit != null) {
            wallConfig.setSelectLimit(tempSelectLimit);
        }
        Boolean tempCallAllow = config.getCallAllow() == null ? globalConfig.getCallAllow() : config.getCallAllow();
        if (tempCallAllow != null && !tempCallAllow) {
            wallConfig.setCallAllow(false);
        }
        Boolean tempSelectAllow = config.getSelectAllow() == null ? globalConfig.getSelectAllow() : config.getSelectAllow();
        if (tempSelectAllow != null && !tempSelectAllow) {
            wallConfig.setSelelctAllow(false);
        }
        Boolean tempSelectIntoAllow = config.getSelectIntoAllow() == null ? globalConfig.getSelectIntoAllow() : config.getSelectIntoAllow();
        if (tempSelectIntoAllow != null && !tempSelectIntoAllow) {
            wallConfig.setSelectIntoAllow(false);
        }
        Boolean tempSelectIntoOutfileAllow = config.getSelectIntoOutfileAllow() == null ? globalConfig.getSelectIntoOutfileAllow() : config.getSelectIntoOutfileAllow();
        if (tempSelectIntoOutfileAllow != null && tempSelectIntoOutfileAllow) {
            wallConfig.setSelectIntoOutfileAllow(true);
        }
        Boolean tempSelectWhereAlwayTrueCheck = config.getSelectWhereAlwayTrueCheck() == null ? globalConfig.getSelectWhereAlwayTrueCheck() : config.getSelectWhereAlwayTrueCheck();
        if (tempSelectWhereAlwayTrueCheck != null && !tempSelectWhereAlwayTrueCheck) {
            wallConfig.setSelectWhereAlwayTrueCheck(false);
        }
        Boolean tempSelectHavingAlwayTrueCheck = config.getSelectHavingAlwayTrueCheck() == null ? globalConfig.getSelectHavingAlwayTrueCheck() : config.getSelectHavingAlwayTrueCheck();
        if (tempSelectHavingAlwayTrueCheck != null && !tempSelectHavingAlwayTrueCheck) {
            wallConfig.setSelectHavingAlwayTrueCheck(false);
        }
        Boolean tempSelectUnionCheck = config.getSelectUnionCheck() == null ? globalConfig.getSelectUnionCheck() : config.getSelectUnionCheck();
        if (tempSelectUnionCheck != null && !tempSelectUnionCheck) {
            wallConfig.setSelectUnionCheck(false);
        }
        Boolean tempSelectMinusCheck = config.getSelectMinusCheck() == null ? globalConfig.getSelectMinusCheck() : config.getSelectMinusCheck();
        if (tempSelectMinusCheck != null && !tempSelectMinusCheck) {
            wallConfig.setSelectMinusCheck(false);
        }
        Boolean tempSelectExceptCheck = config.getSelectExceptCheck() == null ? globalConfig.getSelectExceptCheck() : config.getSelectExceptCheck();
        if (tempSelectExceptCheck != null && !tempSelectExceptCheck) {
            wallConfig.setSelectExceptCheck(false);
        }
        Boolean tempSelectIntersectCheck = config.getSelectIntersectCheck() == null ? globalConfig.getSelectIntersectCheck() : config.getSelectIntersectCheck();
        if (tempSelectIntersectCheck != null && !tempSelectIntersectCheck) {
            wallConfig.setSelectIntersectCheck(false);
        }
        Boolean tempCreateTableAllow = config.getCreateTableAllow() == null ? globalConfig.getCreateTableAllow() : config.getCreateTableAllow();
        if (tempCreateTableAllow != null && !tempCreateTableAllow) {
            wallConfig.setCreateTableAllow(false);
        }
        Boolean tempDropTableAllow = config.getDropTableAllow() == null ? globalConfig.getDropTableAllow() : config.getDropTableAllow();
        if (tempDropTableAllow != null && !tempDropTableAllow) {
            wallConfig.setDropTableAllow(false);
        }
        Boolean tempAlterTableAllow = config.getAlterTableAllow() == null ? globalConfig.getAlterTableAllow() : config.getAlterTableAllow();
        if (tempAlterTableAllow != null && !tempAlterTableAllow) {
            wallConfig.setAlterTableAllow(false);
        }
        Boolean tempRenameTableAllow = config.getRenameTableAllow() == null ? globalConfig.getRenameTableAllow() : config.getRenameTableAllow();
        if (tempRenameTableAllow != null && !tempRenameTableAllow) {
            wallConfig.setRenameTableAllow(false);
        }
        Boolean tempHintAllow = config.getHintAllow() == null ? globalConfig.getHintAllow() : config.getHintAllow();
        if (tempHintAllow != null && !tempHintAllow) {
            wallConfig.setHintAllow(false);
        }
        Boolean tempLockTableAllow = config.getLockTableAllow() == null ? globalConfig.getLockTableAllow() : config.getLockTableAllow();
        if (tempLockTableAllow != null && !tempLockTableAllow) {
            wallConfig.setLockTableAllow(false);
        }
        Boolean tempStartTransactionAllow = config.getStartTransactionAllow() == null ? globalConfig.getStartTransactionAllow() : config.getStartTransactionAllow();
        if (tempStartTransactionAllow != null && !tempStartTransactionAllow) {
            wallConfig.setStartTransactionAllow(false);
        }
        Boolean tempBlockAllow = config.getBlockAllow() == null ? globalConfig.getBlockAllow() : config.getBlockAllow();
        if (tempBlockAllow != null && !tempBlockAllow) {
            wallConfig.setBlockAllow(false);
        }
        Boolean tempConditionAndAlwayTrueAllow = config.getConditionAndAlwayTrueAllow() == null ? globalConfig.getConditionAndAlwayTrueAllow() : config.getConditionAndAlwayTrueAllow();
        if (tempConditionAndAlwayTrueAllow != null && tempConditionAndAlwayTrueAllow) {
            wallConfig.setConditionAndAlwayTrueAllow(true);
        }
        Boolean tempConditionAndAlwayFalseAllow = config.getConditionAndAlwayFalseAllow() == null ? globalConfig.getConditionAndAlwayFalseAllow() : config.getConditionAndAlwayFalseAllow();
        if (tempConditionAndAlwayFalseAllow != null && tempConditionAndAlwayFalseAllow) {
            wallConfig.setConditionAndAlwayFalseAllow(true);
        }
        Boolean tempConditionDoubleConstAllow = config.getConditionDoubleConstAllow() == null ? globalConfig.getConditionDoubleConstAllow() : config.getConditionDoubleConstAllow();
        if (tempConditionDoubleConstAllow != null && tempConditionDoubleConstAllow) {
            wallConfig.setConditionDoubleConstAllow(true);
        }
        Boolean tempConditionLikeTrueAllow = config.getConditionLikeTrueAllow() == null ? globalConfig.getConditionLikeTrueAllow() : config.getConditionLikeTrueAllow();
        if (tempConditionLikeTrueAllow != null && !tempConditionLikeTrueAllow) {
            wallConfig.setConditionLikeTrueAllow(false);
        }
        Boolean tempSelectAllColumnAllow = config.getSelectAllColumnAllow() == null ? globalConfig.getSelectAllColumnAllow() : config.getSelectAllColumnAllow();
        if (tempSelectAllColumnAllow != null && !tempSelectAllColumnAllow) {
            wallConfig.setSelectAllColumnAllow(false);
        }
        Boolean tempDeleteAllow = config.getDeleteAllow() == null ? globalConfig.getDeleteAllow() : config.getDeleteAllow();
        if (tempDeleteAllow != null && !tempDeleteAllow) {
            wallConfig.setDeleteAllow(false);
        }
        Boolean tempDeleteWhereAlwayTrueCheck = config.getDeleteWhereAlwayTrueCheck() == null ? globalConfig.getDeleteWhereAlwayTrueCheck() : config.getDeleteWhereAlwayTrueCheck();
        if (tempDeleteWhereAlwayTrueCheck != null && !tempDeleteWhereAlwayTrueCheck) {
            wallConfig.setDeleteWhereAlwayTrueCheck(false);
        }
        Boolean tempDeleteWhereNoneCheck = config.getDeleteWhereNoneCheck() == null ? globalConfig.getDeleteWhereNoneCheck() : config.getDeleteWhereNoneCheck();
        if (tempDeleteWhereNoneCheck != null && tempDeleteWhereNoneCheck) {
            wallConfig.setDeleteWhereNoneCheck(true);
        }
        Boolean tempUpdateAllow = config.getUpdateAllow() == null ? globalConfig.getUpdateAllow() : config.getUpdateAllow();
        if (tempUpdateAllow != null && !tempUpdateAllow) {
            wallConfig.setUpdateAllow(false);
        }
        Boolean tempUpdateWhereAlayTrueCheck = config.getUpdateWhereAlayTrueCheck() == null ? globalConfig.getUpdateWhereAlayTrueCheck() : config.getUpdateWhereAlayTrueCheck();
        if (tempUpdateWhereAlayTrueCheck != null && !tempUpdateWhereAlayTrueCheck) {
            wallConfig.setUpdateWhereAlayTrueCheck(false);
        }
        Boolean tempUpdateWhereNoneCheck = config.getUpdateWhereNoneCheck() == null ? globalConfig.getUpdateWhereNoneCheck() : config.getUpdateWhereNoneCheck();
        if (tempUpdateWhereNoneCheck != null && tempUpdateWhereNoneCheck) {
            wallConfig.setUpdateWhereNoneCheck(true);
        }
        Boolean tempInsertAllow = config.getInsertAllow() == null ? globalConfig.getInsertAllow() : config.getInsertAllow();
        if (tempInsertAllow != null && !tempInsertAllow) {
            wallConfig.setInsertAllow(false);
        }
        Boolean tempMergeAllow = config.getMergeAllow() == null ? globalConfig.getMergeAllow() : config.getMergeAllow();
        if (tempMergeAllow != null && !tempMergeAllow) {
            wallConfig.setMergeAllow(false);
        }
        Boolean tempMinusAllow = config.getMinusAllow() == null ? globalConfig.getMinusAllow() : config.getMinusAllow();
        if (tempMinusAllow != null && !tempMinusAllow) {
            wallConfig.setMinusAllow(false);
        }
        Boolean tempIntersectAllow = config.getIntersectAllow() == null ? globalConfig.getIntersectAllow() : config.getIntersectAllow();
        if (tempIntersectAllow != null && !tempIntersectAllow) {
            wallConfig.setIntersectAllow(false);
        }
        Boolean tempReplaceAllow = config.getReplaceAllow() == null ? globalConfig.getReplaceAllow() : config.getReplaceAllow();
        if (tempReplaceAllow != null && !tempReplaceAllow) {
            wallConfig.setReplaceAllow(false);
        }
        Boolean tempSetAllow = config.getSetAllow() == null ? globalConfig.getSetAllow() : config.getSetAllow();
        if (tempSetAllow != null && !tempSetAllow) {
            wallConfig.setSetAllow(false);
        }
        Boolean tempCommitAllow = config.getCommitAllow() == null ? globalConfig.getCommitAllow() : config.getCommitAllow();
        if (tempCommitAllow != null && !tempCommitAllow) {
            wallConfig.setCommitAllow(false);
        }
        Boolean tempRollbackAllow = config.getRollbackAllow() == null ? globalConfig.getRollbackAllow() : config.getRollbackAllow();
        if (tempRollbackAllow != null && !tempRollbackAllow) {
            wallConfig.setRollbackAllow(false);
        }
        Boolean tempUseAllow = config.getUseAllow() == null ? globalConfig.getUseAllow() : config.getUseAllow();
        if (tempUseAllow != null && !tempUseAllow) {
            wallConfig.setUseAllow(false);
        }
        Boolean tempMultiStatementAllow = config.getMultiStatementAllow() == null ? globalConfig.getMultiStatementAllow() : config.getMultiStatementAllow();
        if (tempMultiStatementAllow != null && tempMultiStatementAllow) {
            wallConfig.setMultiStatementAllow(true);
        }
        Boolean tempTruncateAllow = config.getTruncateAllow() == null ? globalConfig.getTruncateAllow() : config.getTruncateAllow();
        if (tempTruncateAllow != null && !tempTruncateAllow) {
            wallConfig.setTruncateAllow(false);
        }
        Boolean tempCommentAllow = config.getCommentAllow() == null ? globalConfig.getCommentAllow() : config.getCommentAllow();
        if (tempCommentAllow != null && tempCommentAllow) {
            wallConfig.setCommentAllow(true);
        }
        Boolean tempStrictSyntaxCheck = config.getStrictSyntaxCheck() == null ? globalConfig.getStrictSyntaxCheck() : config.getStrictSyntaxCheck();
        if (tempStrictSyntaxCheck != null && !tempStrictSyntaxCheck) {
            wallConfig.setStrictSyntaxCheck(false);
        }
        Boolean tempConstArithmeticAllow = config.getConstArithmeticAllow() == null ? globalConfig.getConstArithmeticAllow() : config.getConstArithmeticAllow();
        if (tempConstArithmeticAllow != null && !tempConstArithmeticAllow) {
            wallConfig.setConstArithmeticAllow(false);
        }
        Boolean tempLimitZeroAllow = config.getLimitZeroAllow() == null ? globalConfig.getLimitZeroAllow() : config.getLimitZeroAllow();
        if (tempLimitZeroAllow != null && tempLimitZeroAllow) {
            wallConfig.setLimitZeroAllow(true);
        }
        Boolean tempDescribeAllow = config.getDescribeAllow() == null ? globalConfig.getDescribeAllow() : config.getDescribeAllow();
        if (tempDescribeAllow != null && !tempDescribeAllow) {
            wallConfig.setDescribeAllow(false);
        }
        Boolean tempShowAllow = config.getShowAllow() == null ? globalConfig.getShowAllow() : config.getShowAllow();
        if (tempShowAllow != null && !tempShowAllow) {
            wallConfig.setShowAllow(false);
        }
        Boolean tempSchemaCheck = config.getSchemaCheck() == null ? globalConfig.getSchemaCheck() : config.getSchemaCheck();
        if (tempSchemaCheck != null && !tempSchemaCheck) {
            wallConfig.setSchemaCheck(false);
        }
        Boolean tempTableCheck = config.getTableCheck() == null ? globalConfig.getTableCheck() : config.getTableCheck();
        if (tempTableCheck != null && !tempTableCheck) {
            wallConfig.setTableCheck(false);
        }
        Boolean tempFunctionCheck = config.getFunctionCheck() == null ? globalConfig.getFunctionCheck() : config.getFunctionCheck();
        if (tempFunctionCheck != null && !tempFunctionCheck) {
            wallConfig.setFunctionCheck(false);
        }
        Boolean tempObjectCheck = config.getObjectCheck() == null ? globalConfig.getObjectCheck() : config.getObjectCheck();
        if (tempObjectCheck != null && !tempObjectCheck) {
            wallConfig.setObjectCheck(false);
        }
        Boolean tempVariantCheck = config.getVariantCheck() == null ? globalConfig.getVariantCheck() : config.getVariantCheck();
        if (tempVariantCheck != null && !tempVariantCheck) {
            wallConfig.setVariantCheck(false);
        }
        Boolean tempMustParameterized = config.getMustParameterized() == null ? globalConfig.getMustParameterized() : config.getMustParameterized();
        if (tempMustParameterized != null && tempMustParameterized) {
            wallConfig.setMustParameterized(true);
        }
        Boolean tempDoPrivilegedAllow = config.getDoPrivilegedAllow() == null ? globalConfig.getDoPrivilegedAllow() : config.getDoPrivilegedAllow();
        if (tempDoPrivilegedAllow != null && tempDoPrivilegedAllow) {
            wallConfig.setDoPrivilegedAllow(true);
        }
        Boolean tempWrapAllow = config.getWrapAllow() == null ? globalConfig.getWrapAllow() : config.getWrapAllow();
        if (tempWrapAllow != null && !tempWrapAllow) {
            wallConfig.setWrapAllow(false);
        }
        Boolean tempMetadataAllow = config.getMetadataAllow() == null ? globalConfig.getMetadataAllow() : config.getMetadataAllow();
        if (tempMetadataAllow != null && !tempMetadataAllow) {
            wallConfig.setMetadataAllow(false);
        }
        Boolean tempConditionOpXorAllow = config.getConditionOpXorAllow() == null ? globalConfig.getConditionOpXorAllow() : config.getConditionOpXorAllow();
        if (tempConditionOpXorAllow != null && tempConditionOpXorAllow) {
            wallConfig.setConditionOpXorAllow(true);
        }
        Boolean tempConditionOpBitwseAllow = config.getConditionOpBitwseAllow() == null ? globalConfig.getConditionOpBitwseAllow() : config.getConditionOpBitwseAllow();
        if (tempConditionOpBitwseAllow != null && !tempConditionOpBitwseAllow) {
            wallConfig.setConditionOpBitwseAllow(false);
        }
        Boolean tempCaseConditionConstAllow = config.getCaseConditionConstAllow() == null ? globalConfig.getCaseConditionConstAllow() : config.getCaseConditionConstAllow();
        if (tempCaseConditionConstAllow != null && tempCaseConditionConstAllow) {
            wallConfig.setCaseConditionConstAllow(true);
        }
        Boolean tempCompleteInsertValuesCheck = config.getCompleteInsertValuesCheck() == null ? globalConfig.getCompleteInsertValuesCheck() : config.getCompleteInsertValuesCheck();
        if (tempCompleteInsertValuesCheck != null && tempCompleteInsertValuesCheck) {
            wallConfig.setCompleteInsertValuesCheck(true);
        }
        return wallConfig;
    }
}

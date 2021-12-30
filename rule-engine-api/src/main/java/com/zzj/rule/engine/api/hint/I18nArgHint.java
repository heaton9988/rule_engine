package com.zzj.rule.engine.api.hint;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzj.rule.engine.api.utils.ArrayUtils;
import com.zzj.rule.engine.api.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * I18n多语言Hint及处理
 */
@Slf4j
public class I18nArgHint implements ArgHint {
    private static final String I18N_HINT = "i18n";

    private static final List<Locale> DEFAULT_LOCALES = Lists.newArrayList(
        Locale.CHINESE,
        Locale.ENGLISH,
        Locale.JAPAN
    );

    private final List<Locale> supportLocales;

    private final I18nTranslator i18nTranslator;

    public I18nArgHint() {
        this(DEFAULT_LOCALES, new DefaultI18nTranslator());
    }

    public I18nArgHint(I18nTranslator i18nTranslator) {
        this(DEFAULT_LOCALES, i18nTranslator);
    }

    public I18nArgHint(List<Locale> supportLocales, I18nTranslator i18nTranslator) {
        this.supportLocales = supportLocales;
        this.i18nTranslator = i18nTranslator;
    }

    @Override
    public int order() {
        return 100;
    }

    @Override
    public boolean matchHint(String[] hints) {
        return ArrayUtils.contains(hints, I18N_HINT);
    }

    @Override
    public ArgHintHandler getHandler() {
        return commandArg -> {
            if (commandArg.isFrozen()) {
                // 如果指令已经被其他Hint冻结, 这里不再执行
                return;
            }
            Map<String, String> i18nOriginScript = Maps.newHashMap();
            String evalScript = commandArg.getEvalScript();
            for (Locale locale : supportLocales) {
                // 因为这里是多语种规则翻译, 需要多次执行脚本, 先强制解冻
                commandArg.unfreeze();
                commandArg.setEvalScript(i18nTranslator.translate(locale, evalScript));
                i18nOriginScript.put(locale.getLanguage(), commandArg.eval());
            }
            commandArg.setEvalScript(JSONUtils.toJSONString(i18nOriginScript));
        };
    }

    /**
     * 文案翻译器.
     */
    public interface I18nTranslator {
        String translate(Locale locale, String i18nKey);
    }

    public static class DefaultI18nTranslator implements I18nTranslator {
        @Override
        public String translate(Locale locale, String i18nKey) {
            return i18nKey;
        }
    }
}
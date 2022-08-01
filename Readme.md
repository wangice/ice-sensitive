## 脱敏功能
通过注解+AOP的方式，对敏感字段进行脱敏

有两种脱敏的方式

第一种，在Controller层中的方法添加注解，返回值
```
@SensitiveMethod({@SensitiveParam(fields = "user", type = SensitiveType.CHINESE_NAME)})
```

第二种，仅在Controller层的方法添加@SensitiveMethod注解，其他内容需要在配置文件中完成
```
sensitive:
  scan-package: com.ice.framework.web.sensitive  -- 扫描包，如果目标类在该扫描包下参会被脱敏
  fields:                                        -- 内置的脱敏信息，key是系统内部定义字段，value则是用户需要脱敏字段,内部支持了五种，其他可以通过则正来完成
    mobilePhone: phone
    userName: user
    address: address
    email: email
    identityCard: identity
  expands:                                        -- 用户扩展的信息，仅支持正则表达
    - field: city
      regExp: (.{3}).*(.{3})
      regStr: $1*****$2
```

允许对默认脱敏方法进行变更，如默认的方法mobilePhone，即内置转化为PhoneConvert接口，在SensitiveGlobalConfig中默认配置。
需要在自己的方法中调整
```
    @Bean
    public PhoneConvert phoneConvert() {
        return word -> SensitiveUtil.replace(word, 3, 3, SensitiveUtil.DEFAULT_MASK);
    }
```

第一种SensitiveParam下
SensitiveType枚举
CHINESE_NAME： 名字
ID_CARD： 身份证
EMAIL： 邮箱
ADDRESS： 地址
MOBILE_PHONE： 手机号




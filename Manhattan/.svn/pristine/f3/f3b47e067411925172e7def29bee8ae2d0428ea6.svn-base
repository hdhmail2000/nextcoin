
#define SingletonH(classname) + (instancetype)shared##classname;

#define SingletonM(classname)\
\
static classname *_instace = nil;\
\
+ (instancetype)shared##classname\
{\
    if (_instace == nil) {\
        _instace = [[self alloc] init];\
    }\
    return _instace;\
}\
\
+ (instancetype)allocWithZone:(struct _NSZone *)zone\
{\
    static dispatch_once_t onceToken;\
    dispatch_once(&onceToken, ^{\
        _instace = [super allocWithZone:zone];\
    });\
    return _instace;\
}\
\
- (id)copyWithZone:(NSZone *)zone\
{\
    return _instace;\
}


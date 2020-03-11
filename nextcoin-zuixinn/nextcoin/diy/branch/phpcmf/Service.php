<?php namespace Phpcmf;


class Service
{

    /**
     * @var array
     */
    static private $instances = [];

    /**
     * @var object
     */
    static private $view;

    /**
     * @var object
     */
    static private $model;

    /**
     * 控制器对象实例
     *
     * @var object
     */
    public static function C() {
        return \get_instance();
    }

    // 是否是电脑端
    public static function IS_PC() {
        return !static::C()->is_mobile;
    }

    // 是否是移动端
    public static function IS_MOBILE() {
        return static::C()->is_mobile;
    }

    /**
     * 模板视图对象实例
     *
     * @var object
     */
    public static function V() {

        if (!is_object(static::$view)) {
            static::$view = \get_instance()->template;
        }

        return static::$view;
    }

    /**
     * 模型类对象实例
     *
     * @var object
     */
    public static function model() {
        return static::C();
    }

    /**
     * 类对象实例
     *
     * @var object
     */
    public static function L( $name,  $namespace = '') {



    }

    /**
     * 模型对象实例
     *
     * @var object
     */
    public static function M( $name = '',  $namespace = '') {

        if (!$name) {
            if (!isset(static::$instances['phpcmf']) or !is_object(static::$instances['phpcmf'])) {
                static::$instances['phpcmf'] = new \Phpcmf\Model();
            }
            return static::$instances['phpcmf'];
        }

        $_cname = md5($namespace.$name);

        if (!isset(static::$instances[$_cname]) or !is_object(static::$instances[$_cname])) {

            if ($namespace) {
                if (is_dir(FCPATH.'module/'.$namespace.'/')) {
                    static::C()->load->add_package_path(FCPATH.'module/'.$namespace.'/');
                } elseif (is_dir(FCPATH.'app/'.$namespace.'/')) {
                    static::C()->load->add_package_path(FCPATH.'app/'.$namespace.'/');
                }
            }

            static::C()->load->model($name.'_model', 'phpcmf_model');
            $obj = static::C()->phpcmf_model;
            static::$instances[$_cname] = $obj;
        }

        return static::$instances[$_cname];
    }

}
<?php

if (!defined('BASEPATH')) exit('No direct script access allowed');

$active_group	= 'default';
$query_builder	= TRUE;

$db['default']	= array(
    'dsn'		=> 'mysql:host=172.21.196.137;dbname=relang',
    'hostname'	=> '172.21.196.137',
	'username'	=> 'relang',
	'password'	=> 'RelangDe88',
	'port'		=> '3306',
	'database'	=> 'relang',
	'dbdriver'	=> 'mysqli',
	'dbprefix'	=> 'e_',
	'pconnect'	=> FALSE,
	'db_debug'	=> true,
	'cache_on'	=> FALSE,
	'cachedir'	=> 'cache/sql/',
	'char_set'	=> 'utf8',
	'dbcollat'	=> 'utf8_general_ci',
	'swap_pre'	=> '',
	'autoinit'	=> FALSE,
	'encrypt'	=> FALSE,
	'compress'	=> FALSE,
	'stricton'	=> FALSE,
	'failover'	=> array(),
);

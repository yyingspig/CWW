import request from '@/utils/request'

// 查询传感器数据列表
export function listData(query) {
  return request({
    url: '/SysControl/data/list',
    method: 'get',
    params: query
  })
}

// 查询传感器数据详细
export function getData(id) {
  return request({
    url: '/SysControl/data/' + id,
    method: 'get'
  })
}

// 新增传感器数据
export function addData(data) {
  return request({
    url: '/SysControl/data',
    method: 'post',
    data: data
  })
}

// 修改传感器数据
export function updateData(data) {
  return request({
    url: '/SysControl/data',
    method: 'put',
    data: data
  })
}

// 删除传感器数据
export function delData(id) {
  return request({
    url: '/SysControl/data/' + id,
    method: 'delete'
  })
}

export function chartList() {
  return request({
    url: '/SysControl/data/test/list',
    method: 'get'
  })
}

export function sendLED(status) {
  return request({
    url: '/SysControl/data/test/switchLED/' + status,
    method: 'get'
  })
}

export function sendSG90(value) {
  return request({
    url: '/SysControl/data/test/duoji/' + value,
    method: 'get'
  })
}

export function sendResetSG90() {
  return request({
    url: '/SysControl/data/test/duoji',
    method: 'get'
  })
}

export function online() {
  return request({
    url: '/SysControl/data/test/online',
    method: 'get'
  })
}

export function online2() {
  return request({
    url: '/SysControl/data/test/online2',
    method: 'get'
  })
}

export function setAutoStatus(nValue) {
  return request({
    url: '/SysControl/data/test/setAutoStatus/' + nValue,
    method: 'get'
  })
}

export function getAutoStatus() {
  return request({
    url: '/SysControl/data/test/getAutoStatus',
    method: 'get'
  })
}

export function updateCron(cron) {
  return request({
    url: '/SysControl/data/test/updateCron',
    method: 'post',
    data: {
      cron: cron
    }
  })
}


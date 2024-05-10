import request from '@/utils/request'

// 查询入住信息列表
export function listBoarding(query) {
  return request({
    url: '/SysControl/boarding/list',
    method: 'get',
    params: query
  })
}

// 查询入住信息详细
export function getBoarding(boardingId) {
  return request({
    url: '/SysControl/boarding/' + boardingId,
    method: 'get'
  })
}

// 新增入住信息
export function addBoarding(data) {
  return request({
    url: '/SysControl/boarding',
    method: 'post',
    data: data
  })
}

// 修改入住信息
export function updateBoarding(data) {
  return request({
    url: '/SysControl/boarding',
    method: 'put',
    data: data
  })
}

// 删除入住信息
export function delBoarding(boardingId) {
  return request({
    url: '/SysControl/boarding/' + boardingId,
    method: 'delete'
  })
}

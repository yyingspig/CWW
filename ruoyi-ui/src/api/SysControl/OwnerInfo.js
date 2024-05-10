import request from '@/utils/request'

// 查询主人信息列表
export function listOwnerInfo(query) {
  return request({
    url: '/SysControl/OwnerInfo/list',
    method: 'get',
    params: query
  })
}

// 查询主人信息详细
export function getOwnerInfo(ownerId) {
  return request({
    url: '/SysControl/OwnerInfo/' + ownerId,
    method: 'get'
  })
}

// 新增主人信息
export function addOwnerInfo(data) {
  return request({
    url: '/SysControl/OwnerInfo',
    method: 'post',
    data: data
  })
}

// 修改主人信息
export function updateOwnerInfo(data) {
  return request({
    url: '/SysControl/OwnerInfo',
    method: 'put',
    data: data
  })
}

// 删除主人信息
export function delOwnerInfo(ownerId) {
  return request({
    url: '/SysControl/OwnerInfo/' + ownerId,
    method: 'delete'
  })
}

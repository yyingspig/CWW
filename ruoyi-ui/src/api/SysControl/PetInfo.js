import request from '@/utils/request'

// 查询宠物信息管理列表
export function listPetInfo(query) {
  return request({
    url: '/SysControl/PetInfo/list',
    method: 'get',
    params: query
  })
}

// 查询宠物信息管理详细
export function getPetInfo(petId) {
  return request({
    url: '/SysControl/PetInfo/' + petId,
    method: 'get'
  })
}

// 新增宠物信息管理
export function addPetInfo(data) {
  return request({
    url: '/SysControl/PetInfo',
    method: 'post',
    data: data
  })
}

// 修改宠物信息管理
export function updatePetInfo(data) {
  return request({
    url: '/SysControl/PetInfo',
    method: 'put',
    data: data
  })
}

// 删除宠物信息管理
export function delPetInfo(petId) {
  return request({
    url: '/SysControl/PetInfo/' + petId,
    method: 'delete'
  })
}

// 查询主人信息列表
export function listOwnerInfo() {
  return request({
    url: '/SysControl/OwnerInfo/list',
    method: 'get',
  })
}

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="宠物名字" prop="petName">
        <el-input
          v-model="queryParams.petName"
          placeholder="请输入宠物名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="种类" prop="species">
        <el-input
          v-model="queryParams.species"
          placeholder="请输入种类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input
          v-model="queryParams.age"
          placeholder="请输入年龄"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="体重" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入体重"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主人id" prop="ownerId">
        <el-input
          v-model="queryParams.ownerId"
          placeholder="请输入主人id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['SysControl:PetInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['SysControl:PetInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['SysControl:PetInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['SysControl:PetInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="PetInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="宠物id" align="center" prop="petId" />
      <el-table-column label="宠物名字" align="center" prop="petName" />
      <el-table-column label="种类" align="center" prop="species" />
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="体重" align="center" prop="weight" />
      <el-table-column label="疫苗接种情况" align="center" prop="vaccinationStatus" />
      <el-table-column label="主人id" align="center" prop="ownerId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['SysControl:PetInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['SysControl:PetInfo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改宠物信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="宠物名字" prop="petName">
          <el-input v-model="form.petName" placeholder="请输入宠物名字" />
        </el-form-item>
        <el-form-item label="种类" prop="species">
          <el-input v-model="form.species" placeholder="请输入种类" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="体重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入体重" />
        </el-form-item>
        <el-form-item label="主人id" prop="ownerId">
          <el-select v-model="form.ownerId" placeholder="请选择主人">
            <el-option
              v-for="owner in owners"
              :key="owner.ownerId"
              :label="`${owner.ownerName}`"
              :value="owner.ownerId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPetInfo, getPetInfo, delPetInfo, addPetInfo, updatePetInfo } from "@/api/SysControl/PetInfo";
import {listOwnerInfo} from "@/api/SysControl/PetInfo";

export default {
  name: "PetInfo",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 宠物信息管理表格数据
      PetInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        petName: null,
        species: null,
        age: null,
        gender: null,
        weight: null,
        vaccinationStatus: null,
        ownerId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        age: [
          { required: true, message: "年龄不能为空", trigger: "blur" }
        ],
      },
      owners: []
    };
  },
  created() {
    this.getList();
    this.fetchOwners(); // 调用查询主人信息的方法
  },
  methods: {
    /** 查询主人信息 */
    fetchOwners() {
      // 调用后端接口获取主人信息列表
      listOwnerInfo().then(response => {
          this.owners = response.rows; // 将返回的主人信息存储到 owners 数组中
        })
        .catch(error => {
          console.error('Error fetching owners:', error);
        });
    },
    /** 查询宠物信息管理列表 */
    getList() {
      this.loading = true;
      listPetInfo(this.queryParams).then(response => {
        this.PetInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        petId: null,
        petName: null,
        species: null,
        age: null,
        gender: null,
        weight: null,
        vaccinationStatus: null,
        ownerId: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.petId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加宠物信息管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const petId = row.petId || this.ids
      getPetInfo(petId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改宠物信息管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.petId != null) {
            updatePetInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPetInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const petIds = row.petId || this.ids;
      this.$modal.confirm('是否确认删除宠物信息管理编号为"' + petIds + '"的数据项？').then(function() {
        return delPetInfo(petIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('SysControl/PetInfo/export', {
        ...this.queryParams
      }, `PetInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

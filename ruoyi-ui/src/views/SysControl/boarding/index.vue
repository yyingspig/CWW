<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="宠物id" prop="petId">
        <el-input
          v-model="queryParams.petId"
          placeholder="请输入宠物id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="宠物屋id" prop="boardingHouseNumber">
        <el-input
          v-model="queryParams.boardingHouseNumber"
          placeholder="请输入宠物屋id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入住时间" prop="checkInDate">
        <el-date-picker clearable
          v-model="queryParams.checkInDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入住时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="入住天数" prop="stayDays">
        <el-input
          v-model="queryParams.stayDays"
          placeholder="请输入入住天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="离开时间" prop="checkOutDate">
        <el-date-picker clearable
          v-model="queryParams.checkOutDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择离开时间">
        </el-date-picker>
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
          v-hasPermi="['SysControl:boarding:add']"
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
          v-hasPermi="['SysControl:boarding:edit']"
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
          v-hasPermi="['SysControl:boarding:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['SysControl:boarding:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="boardingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="入住id" align="center" prop="boardingId" />
      <el-table-column label="宠物id" align="center" prop="petId" />
      <el-table-column label="宠物屋id" align="center" prop="boardingHouseNumber" />
      <el-table-column label="入住时间" align="center" prop="checkInDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkInDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入住天数" align="center" prop="stayDays" />
      <el-table-column label="离开时间" align="center" prop="checkOutDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkOutDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="设备状态" align="center" prop="ownerId">
        <template slot-scope="scope">
          <span>{{ scope.row.ownerId === 1 ? '在线' : '下线' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['SysControl:boarding:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            :disabled="scope.row.ownerId === 0"
            @click="handleControl(scope.row)"
            v-hasPermi="['SysControl:boarding:control']"
          >控制</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['SysControl:boarding:remove']"
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

    <!-- 添加或修改入住信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="宠物id" prop="petId">
          <el-select v-model="form.petId" placeholder="请选择宠物">
            <el-option
              v-for="pet in pets"
              :key="pet.petId"
              :label="`${pet.petName}`"
              :value="pet.petId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="宠物屋id" prop="boardingHouseNumber">
          <el-input v-model="form.boardingHouseNumber" placeholder="请输入宠物屋id" />
        </el-form-item>
        <el-form-item label="入住时间" prop="checkInDate">
          <el-date-picker clearable
            v-model="form.checkInDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入住时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入住天数" prop="stayDays">
          <el-input v-model="form.stayDays" placeholder="请输入入住天数" />
        </el-form-item>
        <el-form-item label="离开时间" prop="checkOutDate">
          <el-date-picker clearable
            v-model="form.checkOutDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择离开时间">
          </el-date-picker>
        </el-form-item>
<!--        <el-form-item label="主人id" prop="ownerId">
          <el-input v-model="form.ownerId" placeholder="请输入主人id" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 控制对话框 -->
    <el-dialog :title="title" :visible.sync="open2" width="500px" append-to-body>
      <el-form ref="form" :rules="rules" label-width="180px">
        <el-form-item label="紫外线灯：">
          <el-radio-group v-model="status">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="0">关闭</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="紫外线灯自动控制：">
          <el-radio-group v-model="autoStatus">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="0">关闭</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="喂食器：">
          <el-button @click="feedPet">喂食</el-button>
          <el-button @click="feedPetTask">自动设置</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改定时任务对话框 -->
    <el-dialog :title="title" :visible.sync="open3" width="800px" append-to-body>
      <el-form ref="form" :model="taskForm" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="cron表达式" prop="cronExpression">
              <el-input v-model="taskForm.cronExpression" placeholder="请输入cron执行表达式">
                <template slot="append">
                  <el-button type="primary" @click="handleShowCron">
                    生成表达式
                    <i class="el-icon-time el-icon--right"></i>
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="taskForm.jobId !== undefined">
            <el-form-item label="状态">
              <el-radio-group v-model="taskForm.status">
                <el-radio
                  v-for="dict in dict.type.sys_job_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="taskSubmitForm">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Cron表达式生成器" :visible.sync="openCron" append-to-body destroy-on-close class="scrollbar">
      <crontab @hide="openCron=false" @fill="crontabFill" :expression="expression"></crontab>
    </el-dialog>
  </div>
</template>

<script>
import { listBoarding, getBoarding, delBoarding, addBoarding, updateBoarding } from "@/api/SysControl/boarding";
import { online, online2, sendLED, sendSG90, sendResetSG90, setAutoStatus, getAutoStatus, updateCron } from '@/api/SysControl/data'
import { listPetInfo } from "@/api/SysControl/PetInfo";
import Crontab from '@/components/Crontab'

export default {
  components: { Crontab },
  name: "Boarding",
  dicts: ['sys_normal_disable'],
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
      // 入住信息表格数据
      boardingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open2: false,
      open3: false,
      // 是否显示Cron表达式弹出层
      openCron: false,
      // 传入的表达式
      expression: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        petId: null,
        boardingHouseNumber: null,
        checkInDate: null,
        stayDays: null,
        checkOutDate: null,
        ownerId: null
      },
      // 表单参数
      form: {},
      taskForm: {},
      // 表单校验
      rules: {
      },
      pets: [],
      status: 2, // 初始状态
      onlineStatus: 0,
      autoStatus: 0
    };
  },
  created() {
    this.getList();
    this.fetchPets(); // 调用查询宠物信息的方法
    this.online();
    this.online2();
    this.getAutoStatus();
  },
  methods: {
    fetchPets() {
      // 调用后端接口获取宠物信息列表
      listPetInfo().then(response => {
        this.pets = response.rows; // 将返回的宠物信息存储到 pets 数组中
      })
        .catch(error => {
          console.error('Error fetching owners:', error);
        });
    },
    /** 查询入住信息列表 */
    getList() {
      this.loading = true;
      listBoarding(this.queryParams).then(response => {
        this.boardingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open2 = false;
      this.reset();
    },
    cancel2() {
      this.open3 = false;
    },
    // 表单重置
    reset() {
      this.form = {
        boardingId: null,
        petId: null,
        boardingHouseNumber: null,
        checkInDate: null,
        stayDays: null,
        checkOutDate: null,
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
      this.ids = selection.map(item => item.boardingId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入住信息";
    },
    /** 控制按钮 */
    handleControl(row) {
      this.title = '控制管理'
      this.open2 = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const boardingId = row.boardingId || this.ids
      getBoarding(boardingId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改入住信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.boardingId != null) {
            updateBoarding(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBoarding(this.form).then(response => {
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
      const boardingIds = row.boardingId || this.ids;
      this.$modal.confirm('是否确认删除入住信息编号为"' + boardingIds + '"的数据项？').then(function() {
        return delBoarding(boardingIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('SysControl/boarding/export', {
        ...this.queryParams
      }, `boarding_${new Date().getTime()}.xlsx`)
    },
    online() {
      online().then(response => {
        console.log(response.data);
        this.status = response.data;
      }).catch(error => {
        console.error('Error online:', error);
      });
    },
    online2() {
      online2().then(response => {
        console.log(response.data);
      }).catch(error => {
        console.error('Error online2:', error);
      });
    },
    getAutoStatus() {
      getAutoStatus().then(response => {
        console.log(response.data);
        this.autoStatus = response.data;
      }).catch(error => {
        console.error('Error getAutoStatus:', error);
      });
    },
    /*setAutoStatus(nValue) {
      setAutoStatus(nValue).then(response => {
        console.log(response.data);
        this.onlineStatus = response.data;
      }).catch(error => {
        console.error('Error setAutoStatus:', error);
      });
    },*/
    feedPet() {
      console.log("喂食器被点击了！");
      sendSG90(0).then(response => {
        console.log(response);
      }).catch(error => {
        console.error('Error feedPet:', error);
      });
    },
    feedPetTask() {
      this.open3 = true;
    },
    controlLed(action) {
      // 调用后端接口，发送控制指令
      sendLED(action).then(response => {
        // 更新LED状态
        this.ledStatus = action === 1 ? 1 : 0;
      }).catch(error => {
        console.error('Error controlling LED:', error);
      });
    },
    /** cron表达式按钮操作 */
    handleShowCron() {
      this.expression = this.taskForm.cronExpression;
      this.openCron = true;
    },
    /** 确定后回传值 */
    crontabFill(value) {
      this.taskForm.cronExpression = value;
    },
    taskSubmitForm() {
      const cron = this.taskForm.cronExpression
      console.log(cron);
      updateCron(cron).then(response => {
        console.log(response);
        this.open3 = false;
      }).catch(error => {
        console.error('Error updateCron:', error);
      });
    }
  },

  watch: {
    'status': function(newValue, oldValue) {
      console.log('LED状态变化：', newValue);
      sendLED(newValue);
    },
    'autoStatus': function (newValue, oldValue) {
      console.log('状态变化：', newValue);
      setAutoStatus(newValue);
    }
  }
};
</script>

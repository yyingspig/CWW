<template>
  <div>
    <div class="button-container">
      <div class="button-wrapper">
        <img v-if="ledStatus === 0" src="@/assets/images/ledoff.jpg" @click="controlLed(1)" />
        <img v-else src="@/assets/images/ledon.jpg" @click="controlLed(0)" />
      </div>
      <div class="button-wrapper">
        <img v-if="SG90Status === 0" src="@/assets/images/ledoff.jpg" @click="controlSG90(1)" />
        <img v-else src="@/assets/images/ledon.jpg" @click="controlSG90(0)" />
      </div>
    </div>
    <div class="button-container">
      <div class="button-wrapper">
        <img src="@/assets/images/ledoff.jpg" @click="resetSG90()" />
      </div>
      <div class="button-wrapper">
        <img v-if="SG90Status === 0" src="@/assets/images/ledoff.jpg" @click="controlSG90(1)" />
        <img v-else src="@/assets/images/ledon.jpg" @click="controlSG90(0)" />
      </div>
    </div>
  </div>
</template>

<script>

import { sendLED, sendSG90, sendResetSG90 } from '@/api/SysControl/data'

export default {
  data() {
    return {
      ledStatus: 0, // 初始状态为关
      SG90Status: 0 // 初始状态为关
    };
  },
  methods: {
    controlLed(action) {
      // 调用后端接口，发送控制指令
      sendLED(action).then(response => {
        // 更新LED状态
        this.ledStatus = action === 1 ? 1 : 0;
      }).catch(error => {
          console.error('Error controlling LED:', error);
        });
    },
    controlSG90(value) {
      // 调用后端接口，发送控制指令
      sendSG90(value === 1 ? 0 : 90).then(response => {
        console.log(response.data);
        // 更新LED状态
        this.SG90Status = value === 1 ? 1 : 0;
      }).catch(error => {
        console.error('Error controlling SG90:', error);
      });
    },
    resetSG90() {
      sendResetSG90().then(response => {
        console.log(response.data);
        // 更新LED状态
        this.SG90Status = value === 1 ? 1 : 0;
      }).catch(error => {
        console.error('Error controlling SG90:', error);
      });
    }
  }
};
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: space-around;
}

.button-wrapper {
  flex: 1;
  text-align: center;
}
</style>

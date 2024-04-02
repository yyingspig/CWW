<template>
  <div>
    <img v-if="ledStatus === 0" src="@/assets/images/ledoff.jpg" @click="controlLed(1)" />
    <img v-else src="@/assets/images/ledon.jpg" @click="controlLed(0)" />
    <!-- 其他传感器的图片控制类似，可以依此类推 -->
  </div>
</template>

<script>

import {sendLED} from '@/api/SysControl/data';

export default {
  data() {
    return {
      ledStatus: 0 // 初始状态为关
    };
  },
  methods: {
    controlLed(action) {
      // 调用后端接口，发送控制指令
      sendLED(action).then(response => {
        console.log(response.data);
        // 更新LED状态
        this.ledStatus = action === 1 ? 1 : 0;
      })
        .catch(error => {
          console.error('Error controlling LED:', error);
        });
    }
    // 其他传感器的控制方法类似，可以依此类推
  }
};
</script>

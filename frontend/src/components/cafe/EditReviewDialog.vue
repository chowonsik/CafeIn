<template>
  <div class="q-pa-md q-gutter-sm">
    <q-btn flat round icon="more_vert" @click="show()"></q-btn>
  </div>
</template>

<script>
import { useQuasar } from 'quasar'
export default {
  name: "EditReviewDialog",
    setup () {
    const $q = useQuasar()

    function show (grid) {
      $q.bottomSheet({
        grid,
        actions: [
          {
            label: '수정하기',
            icon: 'autorenew',
            classes: [ 'edit-word' ]
          },
          {
            label: '삭제하기',
            icon: 'delete',
            classes: [ 'delete-word' ]
          },
          {},
          {
            label: '취소하기',
            icon: 'cancel',
            classes: [ 'edit-word' ]
          },
        ]
      }).onOk(action => {
        // console.log('Action chosen:', action.id)
      }).onCancel(() => {
        // console.log('Dismissed')
      }).onDismiss(() => {
        // console.log('I am triggered on both OK and Cancel')
      })
    }

    return { show }
  }
}
</script>

<style>
.edit-word {
  text-align: center;
}
.delete-word{
  text-align: center;
  color: red;
}
</style>
import { useCallback, useEffect, useState } from '@lynx-js/react'
import { localStorage, sessionStorage } from './utils/storage.js'

import './App.css'
import arrow from './assets/arrow.png'
import lynxLogo from './assets/lynx-logo.png'
import reactLynxLogo from './assets/react-logo.png'

export function App() {
  const [alterLogo, setAlterLogo] = useState(false)
  const [myName, setName] = useState('aaa')
  const [myName1, setName1] = useState('bbb')

  useEffect(() => {
    console.info('Hello, ReactLynx')
  }, [])

  const onTap = useCallback(() => {
    'background only'
    setAlterLogo(!alterLogo)
  }, [alterLogo])

  const onTapName = useCallback(() => {
    'background only'
    console.log('NativeLocalStorageModule =', NativeModules.NativeLocalStorageModule)
    console.log('NativeLocalStorageModule.getItem =', NativeModules.NativeLocalStorageModule.getItem)
    console.log('NativeLocalStorageModule.setItem =', NativeModules.NativeLocalStorageModule.setItem)

    let name = NativeModules.NativeLocalStorageModule.getItem('myName')
    if (!name) name = 'haha'
    name += 'aaa'
    console.log('name =', name)
    NativeModules.NativeLocalStorageModule.setItem('myName', name)
    // if (!localStorage.myName) localStorage.myName = 'aaa'
    // const name = localStorage.myName + ' aa'
    // localStorage.myName = name
    setName(name)
  }, [myName])

  const onTapName1 = useCallback(() => {
    'background only'
    if (!sessionStorage.myName1) sessionStorage.myName1 = 'bbb'
    const name = sessionStorage.myName1 + ' bb'
    sessionStorage.myName1 = name
    setName1(name)
  }, [myName1])

  return (
    <view>
      <view className='Background' />
      <view className='App'>
        <view className='Banner'>
          <view className='Logo' bindtap={onTap}>
            {alterLogo
              ? <image src={reactLynxLogo} className='Logo--react' />
              : <image src={lynxLogo} className='Logo--lynx' />}
          </view>
          <text className='Title'>React</text>
          <text className='Subtitle'>on Lynx</text>
          <view bindtap={onTapName}>
            <text>点击切换名称</text>
            <text className='Subtitle'>{myName}</text>
          </view>
        </view>
        <view className='Content'>
          <image src={arrow} className='Arrow' />
          <text className='Description'>点击徽标，享受乐趣!</text>
          <text className='Hint'>
            编辑<text style={{ fontStyle: 'italic' }}>{' src/App.tsx '}</text>
            查看更新!
          </text>
        </view>
        <view style={{ flex: 1 }}></view>
      </view>
    </view>
  )
}

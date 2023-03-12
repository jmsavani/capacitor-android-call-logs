# cap-android-call-logs

This plugin is generally useful when you want to get call logs in android device. This is only compatible in android devices.

## Install

```bash
npm install cap-android-call-logs
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`checkPermission()`](#checkpermission)
* [`isEnabled()`](#isenabled)
* [`getCallLogs(...)`](#getcalllogs)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### checkPermission()

```typescript
checkPermission() => Promise<{ checkPermission: string; }>
```

**Returns:** <code>Promise&lt;{ checkPermission: string; }&gt;</code>

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<{ isEnabled: boolean; }>
```

**Returns:** <code>Promise&lt;{ isEnabled: boolean; }&gt;</code>

--------------------


### getCallLogs(...)

```typescript
getCallLogs(options: { date?: number; hour?: number; minute?: number; second?: number; }) => Promise<any>
```

| Param         | Type                                                                             |
| ------------- | -------------------------------------------------------------------------------- |
| **`options`** | <code>{ date?: number; hour?: number; minute?: number; second?: number; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------

</docgen-api>

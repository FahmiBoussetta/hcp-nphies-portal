import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IListRoleCodeEnum, defaultValue } from 'app/shared/model/list-role-code-enum.model';

export const ACTION_TYPES = {
  FETCH_LISTROLECODEENUM_LIST: 'listRoleCodeEnum/FETCH_LISTROLECODEENUM_LIST',
  FETCH_LISTROLECODEENUM: 'listRoleCodeEnum/FETCH_LISTROLECODEENUM',
  CREATE_LISTROLECODEENUM: 'listRoleCodeEnum/CREATE_LISTROLECODEENUM',
  UPDATE_LISTROLECODEENUM: 'listRoleCodeEnum/UPDATE_LISTROLECODEENUM',
  PARTIAL_UPDATE_LISTROLECODEENUM: 'listRoleCodeEnum/PARTIAL_UPDATE_LISTROLECODEENUM',
  DELETE_LISTROLECODEENUM: 'listRoleCodeEnum/DELETE_LISTROLECODEENUM',
  RESET: 'listRoleCodeEnum/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IListRoleCodeEnum>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ListRoleCodeEnumState = Readonly<typeof initialState>;

// Reducer

export default (state: ListRoleCodeEnumState = initialState, action): ListRoleCodeEnumState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LISTROLECODEENUM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LISTROLECODEENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LISTROLECODEENUM):
    case REQUEST(ACTION_TYPES.UPDATE_LISTROLECODEENUM):
    case REQUEST(ACTION_TYPES.DELETE_LISTROLECODEENUM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_LISTROLECODEENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LISTROLECODEENUM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LISTROLECODEENUM):
    case FAILURE(ACTION_TYPES.CREATE_LISTROLECODEENUM):
    case FAILURE(ACTION_TYPES.UPDATE_LISTROLECODEENUM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_LISTROLECODEENUM):
    case FAILURE(ACTION_TYPES.DELETE_LISTROLECODEENUM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTROLECODEENUM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTROLECODEENUM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LISTROLECODEENUM):
    case SUCCESS(ACTION_TYPES.UPDATE_LISTROLECODEENUM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_LISTROLECODEENUM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LISTROLECODEENUM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/list-role-code-enums';

// Actions

export const getEntities: ICrudGetAllAction<IListRoleCodeEnum> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LISTROLECODEENUM_LIST,
  payload: axios.get<IListRoleCodeEnum>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IListRoleCodeEnum> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LISTROLECODEENUM,
    payload: axios.get<IListRoleCodeEnum>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IListRoleCodeEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LISTROLECODEENUM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IListRoleCodeEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LISTROLECODEENUM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IListRoleCodeEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_LISTROLECODEENUM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IListRoleCodeEnum> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LISTROLECODEENUM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

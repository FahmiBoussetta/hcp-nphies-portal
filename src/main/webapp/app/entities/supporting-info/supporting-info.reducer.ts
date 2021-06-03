import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISupportingInfo, defaultValue } from 'app/shared/model/supporting-info.model';

export const ACTION_TYPES = {
  FETCH_SUPPORTINGINFO_LIST: 'supportingInfo/FETCH_SUPPORTINGINFO_LIST',
  FETCH_SUPPORTINGINFO: 'supportingInfo/FETCH_SUPPORTINGINFO',
  CREATE_SUPPORTINGINFO: 'supportingInfo/CREATE_SUPPORTINGINFO',
  UPDATE_SUPPORTINGINFO: 'supportingInfo/UPDATE_SUPPORTINGINFO',
  PARTIAL_UPDATE_SUPPORTINGINFO: 'supportingInfo/PARTIAL_UPDATE_SUPPORTINGINFO',
  DELETE_SUPPORTINGINFO: 'supportingInfo/DELETE_SUPPORTINGINFO',
  RESET: 'supportingInfo/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISupportingInfo>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SupportingInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: SupportingInfoState = initialState, action): SupportingInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTINGINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUPPORTINGINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUPPORTINGINFO):
    case REQUEST(ACTION_TYPES.UPDATE_SUPPORTINGINFO):
    case REQUEST(ACTION_TYPES.DELETE_SUPPORTINGINFO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SUPPORTINGINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTINGINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUPPORTINGINFO):
    case FAILURE(ACTION_TYPES.CREATE_SUPPORTINGINFO):
    case FAILURE(ACTION_TYPES.UPDATE_SUPPORTINGINFO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SUPPORTINGINFO):
    case FAILURE(ACTION_TYPES.DELETE_SUPPORTINGINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTINGINFO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPORTINGINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUPPORTINGINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_SUPPORTINGINFO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SUPPORTINGINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUPPORTINGINFO):
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

const apiUrl = 'api/supporting-infos';

// Actions

export const getEntities: ICrudGetAllAction<ISupportingInfo> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUPPORTINGINFO_LIST,
  payload: axios.get<ISupportingInfo>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISupportingInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPORTINGINFO,
    payload: axios.get<ISupportingInfo>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISupportingInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUPPORTINGINFO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISupportingInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUPPORTINGINFO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISupportingInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SUPPORTINGINFO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISupportingInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUPPORTINGINFO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

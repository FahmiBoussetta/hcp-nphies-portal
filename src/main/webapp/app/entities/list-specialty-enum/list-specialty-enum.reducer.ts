import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IListSpecialtyEnum, defaultValue } from 'app/shared/model/list-specialty-enum.model';

export const ACTION_TYPES = {
  FETCH_LISTSPECIALTYENUM_LIST: 'listSpecialtyEnum/FETCH_LISTSPECIALTYENUM_LIST',
  FETCH_LISTSPECIALTYENUM: 'listSpecialtyEnum/FETCH_LISTSPECIALTYENUM',
  CREATE_LISTSPECIALTYENUM: 'listSpecialtyEnum/CREATE_LISTSPECIALTYENUM',
  UPDATE_LISTSPECIALTYENUM: 'listSpecialtyEnum/UPDATE_LISTSPECIALTYENUM',
  PARTIAL_UPDATE_LISTSPECIALTYENUM: 'listSpecialtyEnum/PARTIAL_UPDATE_LISTSPECIALTYENUM',
  DELETE_LISTSPECIALTYENUM: 'listSpecialtyEnum/DELETE_LISTSPECIALTYENUM',
  RESET: 'listSpecialtyEnum/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IListSpecialtyEnum>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ListSpecialtyEnumState = Readonly<typeof initialState>;

// Reducer

export default (state: ListSpecialtyEnumState = initialState, action): ListSpecialtyEnumState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LISTSPECIALTYENUM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LISTSPECIALTYENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LISTSPECIALTYENUM):
    case REQUEST(ACTION_TYPES.UPDATE_LISTSPECIALTYENUM):
    case REQUEST(ACTION_TYPES.DELETE_LISTSPECIALTYENUM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_LISTSPECIALTYENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LISTSPECIALTYENUM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LISTSPECIALTYENUM):
    case FAILURE(ACTION_TYPES.CREATE_LISTSPECIALTYENUM):
    case FAILURE(ACTION_TYPES.UPDATE_LISTSPECIALTYENUM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_LISTSPECIALTYENUM):
    case FAILURE(ACTION_TYPES.DELETE_LISTSPECIALTYENUM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTSPECIALTYENUM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTSPECIALTYENUM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LISTSPECIALTYENUM):
    case SUCCESS(ACTION_TYPES.UPDATE_LISTSPECIALTYENUM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_LISTSPECIALTYENUM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LISTSPECIALTYENUM):
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

const apiUrl = 'api/list-specialty-enums';

// Actions

export const getEntities: ICrudGetAllAction<IListSpecialtyEnum> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LISTSPECIALTYENUM_LIST,
  payload: axios.get<IListSpecialtyEnum>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IListSpecialtyEnum> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LISTSPECIALTYENUM,
    payload: axios.get<IListSpecialtyEnum>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IListSpecialtyEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LISTSPECIALTYENUM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IListSpecialtyEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LISTSPECIALTYENUM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IListSpecialtyEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_LISTSPECIALTYENUM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IListSpecialtyEnum> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LISTSPECIALTYENUM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IListCommunicationReasonEnum, defaultValue } from 'app/shared/model/list-communication-reason-enum.model';

export const ACTION_TYPES = {
  FETCH_LISTCOMMUNICATIONREASONENUM_LIST: 'listCommunicationReasonEnum/FETCH_LISTCOMMUNICATIONREASONENUM_LIST',
  FETCH_LISTCOMMUNICATIONREASONENUM: 'listCommunicationReasonEnum/FETCH_LISTCOMMUNICATIONREASONENUM',
  CREATE_LISTCOMMUNICATIONREASONENUM: 'listCommunicationReasonEnum/CREATE_LISTCOMMUNICATIONREASONENUM',
  UPDATE_LISTCOMMUNICATIONREASONENUM: 'listCommunicationReasonEnum/UPDATE_LISTCOMMUNICATIONREASONENUM',
  PARTIAL_UPDATE_LISTCOMMUNICATIONREASONENUM: 'listCommunicationReasonEnum/PARTIAL_UPDATE_LISTCOMMUNICATIONREASONENUM',
  DELETE_LISTCOMMUNICATIONREASONENUM: 'listCommunicationReasonEnum/DELETE_LISTCOMMUNICATIONREASONENUM',
  RESET: 'listCommunicationReasonEnum/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IListCommunicationReasonEnum>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ListCommunicationReasonEnumState = Readonly<typeof initialState>;

// Reducer

export default (state: ListCommunicationReasonEnumState = initialState, action): ListCommunicationReasonEnumState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LISTCOMMUNICATIONREASONENUM):
    case REQUEST(ACTION_TYPES.UPDATE_LISTCOMMUNICATIONREASONENUM):
    case REQUEST(ACTION_TYPES.DELETE_LISTCOMMUNICATIONREASONENUM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONREASONENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM):
    case FAILURE(ACTION_TYPES.CREATE_LISTCOMMUNICATIONREASONENUM):
    case FAILURE(ACTION_TYPES.UPDATE_LISTCOMMUNICATIONREASONENUM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONREASONENUM):
    case FAILURE(ACTION_TYPES.DELETE_LISTCOMMUNICATIONREASONENUM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LISTCOMMUNICATIONREASONENUM):
    case SUCCESS(ACTION_TYPES.UPDATE_LISTCOMMUNICATIONREASONENUM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONREASONENUM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LISTCOMMUNICATIONREASONENUM):
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

const apiUrl = 'api/list-communication-reason-enums';

// Actions

export const getEntities: ICrudGetAllAction<IListCommunicationReasonEnum> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM_LIST,
  payload: axios.get<IListCommunicationReasonEnum>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IListCommunicationReasonEnum> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LISTCOMMUNICATIONREASONENUM,
    payload: axios.get<IListCommunicationReasonEnum>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IListCommunicationReasonEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LISTCOMMUNICATIONREASONENUM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IListCommunicationReasonEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LISTCOMMUNICATIONREASONENUM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IListCommunicationReasonEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONREASONENUM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IListCommunicationReasonEnum> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LISTCOMMUNICATIONREASONENUM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

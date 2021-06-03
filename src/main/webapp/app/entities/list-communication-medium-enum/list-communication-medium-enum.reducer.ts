import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IListCommunicationMediumEnum, defaultValue } from 'app/shared/model/list-communication-medium-enum.model';

export const ACTION_TYPES = {
  FETCH_LISTCOMMUNICATIONMEDIUMENUM_LIST: 'listCommunicationMediumEnum/FETCH_LISTCOMMUNICATIONMEDIUMENUM_LIST',
  FETCH_LISTCOMMUNICATIONMEDIUMENUM: 'listCommunicationMediumEnum/FETCH_LISTCOMMUNICATIONMEDIUMENUM',
  CREATE_LISTCOMMUNICATIONMEDIUMENUM: 'listCommunicationMediumEnum/CREATE_LISTCOMMUNICATIONMEDIUMENUM',
  UPDATE_LISTCOMMUNICATIONMEDIUMENUM: 'listCommunicationMediumEnum/UPDATE_LISTCOMMUNICATIONMEDIUMENUM',
  PARTIAL_UPDATE_LISTCOMMUNICATIONMEDIUMENUM: 'listCommunicationMediumEnum/PARTIAL_UPDATE_LISTCOMMUNICATIONMEDIUMENUM',
  DELETE_LISTCOMMUNICATIONMEDIUMENUM: 'listCommunicationMediumEnum/DELETE_LISTCOMMUNICATIONMEDIUMENUM',
  RESET: 'listCommunicationMediumEnum/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IListCommunicationMediumEnum>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ListCommunicationMediumEnumState = Readonly<typeof initialState>;

// Reducer

export default (state: ListCommunicationMediumEnumState = initialState, action): ListCommunicationMediumEnumState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_LISTCOMMUNICATIONMEDIUMENUM):
    case REQUEST(ACTION_TYPES.UPDATE_LISTCOMMUNICATIONMEDIUMENUM):
    case REQUEST(ACTION_TYPES.DELETE_LISTCOMMUNICATIONMEDIUMENUM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONMEDIUMENUM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM):
    case FAILURE(ACTION_TYPES.CREATE_LISTCOMMUNICATIONMEDIUMENUM):
    case FAILURE(ACTION_TYPES.UPDATE_LISTCOMMUNICATIONMEDIUMENUM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONMEDIUMENUM):
    case FAILURE(ACTION_TYPES.DELETE_LISTCOMMUNICATIONMEDIUMENUM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_LISTCOMMUNICATIONMEDIUMENUM):
    case SUCCESS(ACTION_TYPES.UPDATE_LISTCOMMUNICATIONMEDIUMENUM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONMEDIUMENUM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_LISTCOMMUNICATIONMEDIUMENUM):
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

const apiUrl = 'api/list-communication-medium-enums';

// Actions

export const getEntities: ICrudGetAllAction<IListCommunicationMediumEnum> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM_LIST,
  payload: axios.get<IListCommunicationMediumEnum>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IListCommunicationMediumEnum> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LISTCOMMUNICATIONMEDIUMENUM,
    payload: axios.get<IListCommunicationMediumEnum>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IListCommunicationMediumEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LISTCOMMUNICATIONMEDIUMENUM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IListCommunicationMediumEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LISTCOMMUNICATIONMEDIUMENUM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IListCommunicationMediumEnum> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_LISTCOMMUNICATIONMEDIUMENUM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IListCommunicationMediumEnum> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LISTCOMMUNICATIONMEDIUMENUM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

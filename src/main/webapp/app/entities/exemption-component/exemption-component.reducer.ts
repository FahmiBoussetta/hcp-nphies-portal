import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExemptionComponent, defaultValue } from 'app/shared/model/exemption-component.model';

export const ACTION_TYPES = {
  FETCH_EXEMPTIONCOMPONENT_LIST: 'exemptionComponent/FETCH_EXEMPTIONCOMPONENT_LIST',
  FETCH_EXEMPTIONCOMPONENT: 'exemptionComponent/FETCH_EXEMPTIONCOMPONENT',
  CREATE_EXEMPTIONCOMPONENT: 'exemptionComponent/CREATE_EXEMPTIONCOMPONENT',
  UPDATE_EXEMPTIONCOMPONENT: 'exemptionComponent/UPDATE_EXEMPTIONCOMPONENT',
  PARTIAL_UPDATE_EXEMPTIONCOMPONENT: 'exemptionComponent/PARTIAL_UPDATE_EXEMPTIONCOMPONENT',
  DELETE_EXEMPTIONCOMPONENT: 'exemptionComponent/DELETE_EXEMPTIONCOMPONENT',
  RESET: 'exemptionComponent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExemptionComponent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ExemptionComponentState = Readonly<typeof initialState>;

// Reducer

export default (state: ExemptionComponentState = initialState, action): ExemptionComponentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EXEMPTIONCOMPONENT):
    case REQUEST(ACTION_TYPES.UPDATE_EXEMPTIONCOMPONENT):
    case REQUEST(ACTION_TYPES.DELETE_EXEMPTIONCOMPONENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_EXEMPTIONCOMPONENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT):
    case FAILURE(ACTION_TYPES.CREATE_EXEMPTIONCOMPONENT):
    case FAILURE(ACTION_TYPES.UPDATE_EXEMPTIONCOMPONENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_EXEMPTIONCOMPONENT):
    case FAILURE(ACTION_TYPES.DELETE_EXEMPTIONCOMPONENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXEMPTIONCOMPONENT):
    case SUCCESS(ACTION_TYPES.UPDATE_EXEMPTIONCOMPONENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_EXEMPTIONCOMPONENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXEMPTIONCOMPONENT):
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

const apiUrl = 'api/exemption-components';

// Actions

export const getEntities: ICrudGetAllAction<IExemptionComponent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT_LIST,
  payload: axios.get<IExemptionComponent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IExemptionComponent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXEMPTIONCOMPONENT,
    payload: axios.get<IExemptionComponent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IExemptionComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXEMPTIONCOMPONENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExemptionComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXEMPTIONCOMPONENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IExemptionComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_EXEMPTIONCOMPONENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExemptionComponent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXEMPTIONCOMPONENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

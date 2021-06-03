import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrganization, defaultValue } from 'app/shared/model/organization.model';

export const ACTION_TYPES = {
  FETCH_ORGANIZATION_LIST: 'organization/FETCH_ORGANIZATION_LIST',
  FETCH_ORGANIZATION: 'organization/FETCH_ORGANIZATION',
  CREATE_ORGANIZATION: 'organization/CREATE_ORGANIZATION',
  UPDATE_ORGANIZATION: 'organization/UPDATE_ORGANIZATION',
  PARTIAL_UPDATE_ORGANIZATION: 'organization/PARTIAL_UPDATE_ORGANIZATION',
  DELETE_ORGANIZATION: 'organization/DELETE_ORGANIZATION',
  RESET: 'organization/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrganization>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type OrganizationState = Readonly<typeof initialState>;

// Reducer

export default (state: OrganizationState = initialState, action): OrganizationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORGANIZATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORGANIZATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ORGANIZATION):
    case REQUEST(ACTION_TYPES.UPDATE_ORGANIZATION):
    case REQUEST(ACTION_TYPES.DELETE_ORGANIZATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ORGANIZATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ORGANIZATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORGANIZATION):
    case FAILURE(ACTION_TYPES.CREATE_ORGANIZATION):
    case FAILURE(ACTION_TYPES.UPDATE_ORGANIZATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ORGANIZATION):
    case FAILURE(ACTION_TYPES.DELETE_ORGANIZATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANIZATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANIZATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORGANIZATION):
    case SUCCESS(ACTION_TYPES.UPDATE_ORGANIZATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ORGANIZATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORGANIZATION):
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

const apiUrl = 'api/organizations';

// Actions

export const getEntities: ICrudGetAllAction<IOrganization> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORGANIZATION_LIST,
  payload: axios.get<IOrganization>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IOrganization> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANIZATION,
    payload: axios.get<IOrganization>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOrganization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORGANIZATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrganization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORGANIZATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOrganization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ORGANIZATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrganization> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORGANIZATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

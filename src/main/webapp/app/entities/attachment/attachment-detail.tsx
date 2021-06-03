import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './attachment.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAttachmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AttachmentDetail = (props: IAttachmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { attachmentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attachmentDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.attachment.detail.title">Attachment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.id}</dd>
          <dt>
            <span id="contentType">
              <Translate contentKey="hcpNphiesPortalApp.attachment.contentType">Content Type</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.contentType}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="hcpNphiesPortalApp.attachment.title">Title</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.title}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="hcpNphiesPortalApp.attachment.language">Language</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.language}</dd>
          <dt>
            <span id="isData">
              <Translate contentKey="hcpNphiesPortalApp.attachment.isData">Is Data</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.isData ? 'true' : 'false'}</dd>
          <dt>
            <span id="dataFile">
              <Translate contentKey="hcpNphiesPortalApp.attachment.dataFile">Data File</Translate>
            </span>
          </dt>
          <dd>
            {attachmentEntity.dataFile ? (
              <div>
                {attachmentEntity.dataFileContentType ? (
                  <a onClick={openFile(attachmentEntity.dataFileContentType, attachmentEntity.dataFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {attachmentEntity.dataFileContentType}, {byteSize(attachmentEntity.dataFile)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="url">
              <Translate contentKey="hcpNphiesPortalApp.attachment.url">Url</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.url}</dd>
          <dt>
            <span id="attachmentSize">
              <Translate contentKey="hcpNphiesPortalApp.attachment.attachmentSize">Attachment Size</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.attachmentSize}</dd>
          <dt>
            <span id="hash">
              <Translate contentKey="hcpNphiesPortalApp.attachment.hash">Hash</Translate>
            </span>
          </dt>
          <dd>
            {attachmentEntity.hash ? (
              <div>
                {attachmentEntity.hashContentType ? (
                  <a onClick={openFile(attachmentEntity.hashContentType, attachmentEntity.hash)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {attachmentEntity.hashContentType}, {byteSize(attachmentEntity.hash)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/attachment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attachment/${attachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ attachment }: IRootState) => ({
  attachmentEntity: attachment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttachmentDetail);
